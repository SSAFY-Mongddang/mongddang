package com.onetwo.mongddang.domain.chatWithMongddang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestAudioDto {

    private byte[] audio;
    private String filename;
    private String mimeType;

}
