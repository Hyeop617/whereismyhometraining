package com.hyeop.whereismyhometraining.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sns {
    NAVER("네이버"),
    GOOGLE("구글"),
    NONE("없음");

    private String text;
}
