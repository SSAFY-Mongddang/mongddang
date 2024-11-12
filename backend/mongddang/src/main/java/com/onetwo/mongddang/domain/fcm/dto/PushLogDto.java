package com.onetwo.mongddang.domain.fcm.dto;

import com.onetwo.mongddang.domain.fcm.model.PushLog;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PushLogDto {
    private Long id;
    private PushLog.Category category;
    private String content;
    private LocalDateTime createdAt;
}
