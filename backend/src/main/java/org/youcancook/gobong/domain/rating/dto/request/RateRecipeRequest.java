package org.youcancook.gobong.domain.rating.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class RateRecipeRequest {
    @NotNull(message = "유저 ID는 필수 항목입니다.")
    private Long userId;

    @NotNull(message = "레시피 ID는 필수 항목입니다.")
    private Long recipeId;

    @NotNull(message = "평점은 필수 항목입니다.")
    @Min(1) @Max(5)
    private Integer score;
}
