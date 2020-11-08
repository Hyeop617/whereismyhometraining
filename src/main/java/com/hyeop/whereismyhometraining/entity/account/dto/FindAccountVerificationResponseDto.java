package com.hyeop.whereismyhometraining.entity.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class FindAccountVerificationResponseDto {
    private Boolean findPassword;

    private String username;

    private String redirectPasswordUri;
}
