package com.onetwo.mongddang.domain.record.dto.meal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestMealStartDto {

    @NotNull(message = "내용은 필수입니다.")
    private String content;

    private String image;

    @NotNull(message = "식사 시간은 필수입니다.")
    @Pattern(regexp = "^(breakfast|lunch|dinner|snack)$", message = "식사 시간은 'breakfast', 'lunch', 'dinner', 'snack' 중 하나여야 합니다.")
    private String mealTime;
}
