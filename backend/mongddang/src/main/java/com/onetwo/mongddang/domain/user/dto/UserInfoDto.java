package com.onetwo.mongddang.domain.user.dto;

import com.onetwo.mongddang.domain.user.model.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class UserInfoDto {

    private String name;
    private User.Role role;
    private String email;
    private String nickname;
    private String invitationCode;
    private LocalDate birth;
    private User.Gender gender;
    private Long mainMongddangId;
    private MaingameDto mainTitle;
    private List<ConnectedUserInfoDto> connected;
}
