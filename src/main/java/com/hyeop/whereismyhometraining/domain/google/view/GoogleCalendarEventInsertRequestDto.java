package com.hyeop.whereismyhometraining.domain.google.view;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GoogleCalendarEventInsertRequestDto {
    private String summary;
    private GoogleCalendarDate start;
    private GoogleCalendarDate end;
}
