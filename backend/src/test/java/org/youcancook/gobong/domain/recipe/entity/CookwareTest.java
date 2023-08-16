package org.youcancook.gobong.domain.recipe.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CookwareTest {
    @Test
    @DisplayName("조리기구 비트를 조리기구 리스트로 변환한다.")
    public void convertBitToCookwares(){
        long cookwareBit = Cookware.OVEN.getValue() | Cookware.MICROWAVE.getValue() | Cookware.ELECTRIC_KETTLE.getValue();
        System.out.println(cookwareBit);
        List<String> actual = Cookware.bitToList(cookwareBit);

        assertThat(actual).hasSize(3);
        assertThat(actual).hasSameElementsAs(
                List.of(Cookware.OVEN.name(), Cookware.MICROWAVE.name(), Cookware.ELECTRIC_KETTLE.name()));
    }
}