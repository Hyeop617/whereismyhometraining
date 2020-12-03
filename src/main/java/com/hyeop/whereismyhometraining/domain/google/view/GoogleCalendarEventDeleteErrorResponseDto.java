package com.hyeop.whereismyhometraining.domain.google.view;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GoogleCalendarEventDeleteErrorResponseDto {
    public Map<String, Object> error;
}
