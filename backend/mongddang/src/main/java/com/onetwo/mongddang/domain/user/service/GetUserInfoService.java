package com.onetwo.mongddang.domain.user.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.game.mongddang.model.Mongddang;
import com.onetwo.mongddang.domain.game.mongddang.model.MyMongddang;
import com.onetwo.mongddang.domain.game.mongddang.repository.MongddangRepository;
import com.onetwo.mongddang.domain.game.mongddang.repository.MyMongddangRepository;
import com.onetwo.mongddang.domain.game.title.model.MyTitle;
import com.onetwo.mongddang.domain.game.title.model.Title;
import com.onetwo.mongddang.domain.game.title.repository.MyTitleRepository;
import com.onetwo.mongddang.domain.game.title.repository.TitleRepository;
import com.onetwo.mongddang.domain.user.dto.ConnectedUserInfoDto;
import com.onetwo.mongddang.domain.user.dto.MaingameDto;
import com.onetwo.mongddang.domain.user.dto.UserInfoDto;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.CtoPRepository;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUserInfoService {

    private final UserRepository userRepository;
    private final MyMongddangRepository myMongddangRepository;
    private final MyTitleRepository myTitleRepository;
    private final CtoPRepository ctoPRepository;
    private final MongddangRepository mongddangRepository;
    private final TitleRepository titleRepository;

    public ResponseDto getUserInfo(Long userId) {
        //사용자
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
        log.info("Existing User : {}", userId);

        UserInfoDto data = getUserInfoDto(user, userId);

        // response
        ResponseDto response = ResponseDto.builder()
                .message("사용자 정보 조회에 성공했습니다.")
                .data(data)
                .build();

        return response;
    }

    public UserInfoDto getUserInfoDto(User user, Long userId) {
        // 메인 몽땅, 메인칭호, 연결 유저 리스트 초기화
        Long mainMongddangId = null;
        MaingameDto mainTitle = null;
        List<User> connectedUserList = List.of();

        // 어린이일 때만 메인몽땅, 메인칭호 제공
        // 연결된 보호자 리스트 뽑아냄
        if ("child".equals(user.getRole().toString())) {
            log.info("child user!");
            // 메인 몽땅
            Optional<MyMongddang> mainMongddangOptional = myMongddangRepository.findByChildIdAndIsMainTrue(userId);

            // 메인 있으면 캐릭터 데이터 뽑기
            if (mainMongddangOptional.isPresent()) {
                log.info("main mongddang is present");
                Optional<Mongddang> mongddang = mongddangRepository.findById(mainMongddangOptional.get().getMongddang().getId());
                // 그 몽땅이 있는 캐릭터라면 정보 뽑아 넣기
                if (mongddang.isPresent()) {
                    log.info("that mongddang is present");
                    mainMongddangId = mongddang.get().getId();
                }
            }

            // 메인 칭호
            Optional<MyTitle> mainTitleOptional = myTitleRepository.findByChildIdAndIsMainTrue(userId);

            // 메인 있으면 칭호 데이터 뽑기
            if (mainTitleOptional.isPresent()) {
                log.info("main title is present");
                Optional<Title> title = titleRepository.findById(mainTitleOptional.get().getTitle().getId());
                // 그 칭호가 있는 칭호라면 뽑아넣기
                if (title.isPresent()) {
                    log.info("that title is present");
                    mainTitle = MaingameDto.builder()
                            .id(title.get().getId())
                            .name(title.get().getName())
                            .build();
                }
            }

            // 연결된 보호자 리스트
            connectedUserList = ctoPRepository.findProtectorByChildId(userId);
        }
        // 보호자일 경우 : 연결된 어린이 리스트 뽑기
        else if ("protector".equals(user.getRole().toString())) {
            // 연결된 어린이 리스트
            connectedUserList = ctoPRepository.findChildByProtectorId(userId);
        }

        // Object에 들어갈 연결상대 리스트 초기화 (상대 없으면 null 반환)
        List<ConnectedUserInfoDto> responseConnectedUserList = null;

        // 연결된 리스트에 값이 있다면
        if (connectedUserList != null && !connectedUserList.isEmpty()) {
            // 반환할 값을 리스트로 선언
            responseConnectedUserList = new ArrayList<>();
            for (User connectedUser : connectedUserList) {
                //반환 형식에 맞게 dto에 담기
                ConnectedUserInfoDto responseUser = ConnectedUserInfoDto.builder()
                        .name(connectedUser.getName())
                        .nickname(connectedUser.getNickname())
                        .build();
                // 리스트에 넣기
                responseConnectedUserList.add(responseUser);
            }
        }

        // response data
        UserInfoDto data = UserInfoDto.builder()
                .name(user.getName())
                .role(user.getRole())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .invitationCode(user.getInvitationCode())
                .birth(user.getBirth())
                .gender(user.getGender())
                .mainMongddangId(mainMongddangId)
                .mainTitle(mainTitle)
                .connected(responseConnectedUserList)
                .build();

        return data;
    }
}
