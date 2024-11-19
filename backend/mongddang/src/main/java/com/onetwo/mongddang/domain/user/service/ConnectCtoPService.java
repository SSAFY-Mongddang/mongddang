package com.onetwo.mongddang.domain.user.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.fcm.model.PushLog;
import com.onetwo.mongddang.domain.fcm.repository.PushLogRepository;
import com.onetwo.mongddang.domain.user.dto.ConnectCtoPDto;
import com.onetwo.mongddang.domain.user.error.CustomCtoPErrorCode;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.CtoP;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.CtoPRepository;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectCtoPService {
    private final UserRepository userRepository;
    private final CtoPRepository ctoPRepository;
    private final PushLogRepository pushLogRepository;

    @Transactional
    public ResponseDto connectCtoP(ConnectCtoPDto connectCtoPDto, Long userId) {

        //사용자
        Optional<User> user = userRepository.findById(userId);
        //초대코드
        String invitationCode = connectCtoPDto.getInvitationCode();
        //초대코드에 연결된 어린이
        Optional<User> child = userRepository.findByInvitationCode(invitationCode);

        // 사용자 유무 판단
        if (user.isEmpty()) {
            throw new RestApiException(CustomUserErrorCode.USER_NOT_FOUND);
        }
        log.info("Existing User : {}", userId);

        // 사용자가 보호자인지 판단
        if (!Objects.equals(user.get().getRole().toString(), "protector")) {
            throw new RestApiException(CustomUserErrorCode.NOT_PROTECTOR);
        }
        log.info("This User is protector");

        //초대코드에 연결된 어린이 유무 판단
        if (child.isEmpty()) {
            throw new RestApiException(CustomCtoPErrorCode.INVALID_INVITATION_CODE);
        }
        log.info("child : {}", child.get().getId());

        // 연결된 유저가 어린이인지 판단
        if (!Objects.equals(child.get().getRole().toString(), "child")) {
            throw new RestApiException(CustomUserErrorCode.NOT_CHILD);
        }
        log.info("Real child!");

        // 이미 연결된 관계는 아닌지 판단
        if (!ctoPRepository.findByChildAndProtector(user.get(), child.get()).isEmpty()) {
            throw new RestApiException(CustomCtoPErrorCode.ALREADY_LINKED);
        }
        log.info(ctoPRepository.findByChildAndProtector(child.get(), user.get()).toString());
        log.info("Connection Start!");

        // 연결 시작
        CtoP newCtoP = CtoP.builder()
                .child(child.get())
                .protector(user.get())
                .build();
        ctoPRepository.save(newCtoP);

        // 연결 알림 해당 어린이 알림함에 쌓아주기
        String content = String.format("'%s'이(가) 보호자로 등록되었어요!", user.get().getNickname());
        log.info(content);
        addConnectNotification(child.get(), content);

        ResponseDto response = ResponseDto.builder()
                .message("보호자-어린이 연결에 성공했습니다.")
                .build();
        return response;
    }

    // 최초 보상 지급 알림 알림함 추가
    @Transactional
    public void addConnectNotification(User child, String content){
        PushLog newPushLog = PushLog.builder()
                .createdAt(LocalDateTime.now())
                .category(PushLog.Category.connect)
                .content(content)
                .user(child)
                .isNew(true)
                .build();
        pushLogRepository.save(newPushLog);
    }
}
