package com.onetwo.mongddang.domain.medication.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.common.s3.S3ImageService;
import com.onetwo.mongddang.domain.medication.application.MedicationUtils;
import com.onetwo.mongddang.domain.medication.dto.MedicationStandardDto;
import com.onetwo.mongddang.domain.medication.dto.RegisteredMedicationDto;
import com.onetwo.mongddang.domain.medication.dto.RequestMedicationDto;
import com.onetwo.mongddang.domain.medication.dto.ResponseMedicationDto;
import com.onetwo.mongddang.domain.medication.errors.CustomMedicationErrorCode;
import com.onetwo.mongddang.domain.medication.model.MedicationManagement;
import com.onetwo.mongddang.domain.medication.model.MedicationTime;
import com.onetwo.mongddang.domain.medication.repository.MedicationManagementRepository;
import com.onetwo.mongddang.domain.medication.repository.MedicationTimeRepository;
import com.onetwo.mongddang.domain.user.application.CtoPUtils;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationService {

    private final UserRepository userRepository;
    private final S3ImageService s3ImageService;
    private final MedicationManagementRepository medicationManagementRepository;
    private final MedicationTimeRepository medicationTimeRepository;
    private final MedicationUtils medicationUtils;
    private final CtoPUtils ctoPUtils;


    @Transactional
    public ResponseDto registerMedication(Long userId, RequestMedicationDto requestMedicationDto) {
        log.info("복약 등록 (In English : Medication Registration)");

//        // JSON 문자열을 RequestRegisterMedicationDto 객체로 변환
//        RequestRegisterMedicationDto requestDto = medicationUtils.jsonToMedicationDto(requestRegisterMedicationDtoJson);

        // 요청자의 정보
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        log.info("요청자의 정보 (In English : Requester's Information)");
        // 환아의 정보
        User child = userRepository.findByNickname(requestMedicationDto.getNickname())
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.THIS_NICKNAME_USER_NOT_FOUND));

        // 접근하려는 유저가 대상의 데이터에 접근 권한이 있는지 확인
        ctoPUtils.validateProtectorAccessChildData(user, child);

        // 이미지 파일이 존재하는 경우 S3에 이미지 파일 업로드
        String imageUrl = null;
//        if (imageFile != null && !imageFile.isEmpty()) {
//            // 이미지 파일을 S3에 업로드
//            try {
//                imageUrl = s3ImageService.upload(imageFile); // MultipartFile을 File로 변환 후 S3에 업로드
//            } catch (Exception e) {
//                throw new RestApiException(CustomS3ErrorCode.IMAGE_UPLOAD_FAILED);
//            }
//        }
//        log.info("식사 이미지 파일 없음 (in english : No meal image file)");

        // 복약 관리 기록 DB 에 저장
        MedicationManagement medicationManagement = MedicationManagement.builder()
                .child(child)
                .name(requestMedicationDto.getName())
                .route(MedicationManagement.RouteType.injection)
                .isRepeated(false)
                .repeatDays(new ArrayList<>())
                .repeatStartTime(requestMedicationDto.getRepeatStartTime())
                .repeatEndTime(requestMedicationDto.getRepeatEndTime())
                .imageUrl(null)
                .build();

        //복약 시간 기록 DB 에 저장
        List<MedicationTime> medicationTimeList = new ArrayList<>();
        for (String repeatTime : requestMedicationDto.getRepeatTimes()) {
            for (MedicationStandardDto medicationStandard : requestMedicationDto.getStandards()) {
                MedicationTime medicationTime = MedicationTime.builder()
                        .medicationManagement(medicationManagement)
                        .medicationTime(repeatTime)
                        .isFast(requestMedicationDto.getIsFast())
                        .minGlucose(requestMedicationDto.getIsFast() ? medicationStandard.getMinGlucose() : null)
                        .maxGlucose(requestMedicationDto.getIsFast() ? medicationStandard.getMaxGlucose() : null)
                        .volume(medicationStandard.getVolume())
                        .build();

                medicationTimeList.add(medicationTime);
            }
        }

        // DB에 저장
        medicationManagementRepository.save(medicationManagement);
        medicationTimeRepository.saveAll(medicationTimeList);

        return ResponseDto.builder()
                .message("약품 등록을 성공하였습니다.")
                .build();
    }


    /**
     * 등록한 약품 조회하기
     *
     * @param userId   유저 아이디
     * @param nickname 어린이 닉네임
     * @return ResponseDto
     */
    public ResponseDto getMedication(Long userId, String nickname) {
        log.info("약품 조회 (In English : Medication Inquiry)");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        User child = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.THIS_NICKNAME_USER_NOT_FOUND));

        // 보호자가 어린이의 데이터에 접근 권한이 있는지 확인
        ctoPUtils.validateProtectorAccessChildData(user, child);

        // 복약 관리 테이블에서 어린이에 해당하는 복약 시간 데이터 조회
        List<MedicationManagement> medicationManagementList = medicationManagementRepository.findByChild(child);
        List<RegisteredMedicationDto> registeredMedicationDtoList = new ArrayList<>();

        // 복약 관리 테이블에서 외래키로 참조한 복약 시간 데이터를 registeredMedicationDtoList 에 데이터 저장
        for (MedicationManagement medicationManagement : medicationManagementList) {
            // 복약 시간 데이터 조회
            List<MedicationTime> medicationTimeList = medicationTimeRepository.findByMedicationManagement(medicationManagement);
            List<MedicationStandardDto> medicationStandardDtoList = new ArrayList<>();

            // 복약 시간 데이터를 MedicationStandardDto 객체로 변환
            for (MedicationTime medicationTime : medicationTimeList) {
                MedicationStandardDto medicationStandardDto = MedicationStandardDto.builder()
                        .minGlucose(medicationTime.getMinGlucose())
                        .maxGlucose(medicationTime.getMaxGlucose())
                        .volume(medicationTime.getVolume())
                        .build();

                medicationStandardDtoList.add(medicationStandardDto);
            }

            // RegisteredMedicationDto 객체 생성
            RegisteredMedicationDto registeredMedicationDto = RegisteredMedicationDto.builder()
                    .id(medicationManagement.getId())
                    .name(medicationManagement.getName())
                    .imageUrl(medicationManagement.getImageUrl())
                    .repeatStartTime(medicationManagement.getRepeatStartTime())
                    .repeatEndTime(medicationManagement.getRepeatEndTime())
                    .isFast(medicationTimeList.size() == 0 ? false : medicationTimeList.getFirst().getIsFast())
                    .repeatTimes(medicationTimeList.size() == 0 ? new ArrayList<>() : medicationTimeList.stream().map(MedicationTime::getMedicationTime).toList())
                    .standards(medicationStandardDtoList)
                    .build();

            registeredMedicationDtoList.add(registeredMedicationDto);
        }

        // ResponseMedicationDto 객체 생성
        ResponseMedicationDto responseMedicationDto = ResponseMedicationDto.builder()
                .medications(registeredMedicationDtoList)
                .build();

        return ResponseDto.builder()
                .message("약품 조회를 성공하였습니다.")
                .data(responseMedicationDto)
                .build();
    }

    /**
     * 등록한 약품 관리 삭제하기
     *
     * @param userId                 유저 아이디
     * @param nickname               어린이 닉네임
     * @param medicationManagementId 복약 관리 아이디
     * @return ResponseDto
     */
    @Transactional
    public ResponseDto deleteMedication(String nickname, Long medicationManagementId, Long userId) {
        log.info("약품 관리 삭제 (In English : Medication Management Deletion)");

        // 요청자의 정보
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        // 어린이의 정보
        User child = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.THIS_NICKNAME_USER_NOT_FOUND));

        // 접근하려는 유저가 대상의 데이터에 접근 권한이 있는지 확인
        ctoPUtils.validateProtectorAccessChildData(user, child);

        // 복약 관리 테이블에서 복약 관리 아이디로 데이터 조회
        log.info("복약 관리 정보 조회 (In English : Medication Management Information Inquiry)");
        MedicationManagement medicationManagement = medicationManagementRepository.findById(medicationManagementId)
                .orElseThrow(() -> new RestApiException(CustomMedicationErrorCode.MEDICATION_NOT_FOUND));

        // 복약 관리 기록이 어린이의 것인지 확인
        log.info("복약 관리 기록이 어린이의 것인지 확인 (In English : Check if the medication management record belongs to the child)");
        if (!medicationManagement.getChild().equals(child)) {
            throw new RestApiException(CustomMedicationErrorCode.MEDICATION_NOT_MATCH);
        }

        log.info("복약 관리 삭제 시도 (In English : Medication Management Deletion Attempt)");
        log.info("managementId : " + medicationManagementId);
        medicationTimeRepository.deleteByMedicationManagementId(medicationManagementId);
        log.info("복약 시간 삭제 시도 (In English : Medication Record Deletion Attempt)");
        medicationManagementRepository.deleteById(medicationManagementId);

        return ResponseDto.builder()
                .message("약품 관리 삭제를 성공하였습니다.")
                .build();
    }

}
