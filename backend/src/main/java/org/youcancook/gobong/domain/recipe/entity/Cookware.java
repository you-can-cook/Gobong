package org.youcancook.gobong.domain.recipe.entity;

import lombok.Getter;

@Getter
public enum Cookware {
    MICROWAVE(0), AIR_FRYER(1), OVEN(2), GAS_RANGE(3),
    MIXER(4), ELECTRIC_KETTLE(5), PAN(6),
    ;

    private final long value;

    Cookware(int power) {
        this.value = 1L << power;
    }



}
