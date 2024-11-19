package com.onetwo.mongddang.domain.game.title.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.game.title.errors.CustomTitleErrorCode;
import com.onetwo.mongddang.domain.game.title.model.MyTitle;
import com.onetwo.mongddang.domain.game.title.repository.MyTitleRepository;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TitleService {

    private final MyTitleRepository myTitleRepository;
    private final UserRepository userRepository;

    /**
     * 메인 칭호 설정
     *
     * @param childId
     * @param titleId
     * @return
     */
    @Transactional
    public ResponseDto setMainTitle(Long childId, Long titleId) {
        log.info("childId: {}, titleId: {}", childId, titleId);

        // 현재 메인 칭호
        MyTitle currentMainTitle = myTitleRepository.findByChildIdAndIsMainTrue(childId)
                .orElseThrow(() -> new RestApiException(CustomTitleErrorCode.NOT_FOUND_TITLE_LIST));
        log.info("currentMainTitle: {}", currentMainTitle.getTitle());

        // 새롭게 설정할 메인 칭호
        MyTitle newMainTitle = myTitleRepository.findByTitleIdAndChildId(titleId, childId)
                .orElseThrow(() -> new RestApiException(CustomTitleErrorCode.TITLE_NOT_OWNED));
        log.info("newMainTitle: {}", newMainTitle.getTitle());

        // 이미 메인 칭호인 경우
        if (newMainTitle.getIsMain()) {
            throw new RestApiException(CustomTitleErrorCode.ALREADY_TITLE_SET_MAIN);
        }

        // 새로운 메인 칭호 설정
        newMainTitle.setIsMain(true);
        // 기존 메인 칭호 해제
        currentMainTitle.setIsMain(false);

        myTitleRepository.save(newMainTitle);
        myTitleRepository.save(currentMainTitle);

        return ResponseDto.builder()
                .message("메인 칭호 설정에 성공했습니다.")
                .build();
    }

}
