package com.onetwo.mongddang.domain.user.application;

import com.onetwo.mongddang.domain.record.errors.CustomRecordErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.CtoPRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CtoPUtils {

    private final CtoPRepository ctoPRepository;

    /**
     * 보호자와 어린이가 연결되어 있는지 확인
     *
     * @param protectorId 보호자 id
     * @param childId     어린이 id
     * @return 연결되어 있으면 true, 아니면 false
     */
    private boolean checkProtectorAndChildIsConnected(Long protectorId, Long childId) {
        log.info("checkProtectorAndChildIsConnected protectorId: {}, childId: {}", protectorId, childId);

        // 보호자와 어린이가 같은 경우
        if (Objects.equals(protectorId, childId)) {
            return true;
        }

        // 보호자의 어린이 리스트
        List<User> childrenList = ctoPRepository.findChildByProtectorId(protectorId);
        log.info("childrenList: {}", childrenList.size());

        // 보호자의 어린이 리스트에 해당 어린이가 있는지 확인
        for (User childInList : childrenList) {
            if (childInList.getId().equals(childId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 접근하려는 유저가 대상의 데이터에 접근 권한이 있는지 확인
     *
     * @param protector 보호자
     * @param child     어린이
     */
    public void validateProtectorAccessChildData(User protector, User child) {
        log.info("validateProtectorAccessChildData protectorId: {}, childId: {}", protector.getId(), child.getId());

        // 어린이는 본인의 기록만 접근할 수 있음
        log.info("어린이가 다른 유저의 기록 접근 시도");
        if (protector.getRole() == User.Role.child && !Objects.equals(protector.getNickname(), child.getNickname())) {
            throw new RestApiException(CustomRecordErrorCode.CHILD_ACCESS_DENIED);
        }

        // 보호자의 기록은 접근할 수 없음 (로직상 어린이만 존재함)
        log.info("보호자의 기록 접근 시도");
        if (child.getRole() == User.Role.protector) {
            throw new RestApiException(CustomRecordErrorCode.PROTECTOR_ACCESS_DENIED);
        }

        // 연결된 보호자인지 확인 - 보호자는 연결된 어린이의 기록만 접근할 수 있음
        log.info("연결된 보호자인지 확인");
        if (!this.checkProtectorAndChildIsConnected(protector.getId(), child.getId())) {
            throw new RestApiException(CustomRecordErrorCode.PROTECTOR_ACCESS_DENIED);
        }

    }


}
