package com.hyeop.whereismyhometraining.domain.google.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoogleCalendarEventResult {

    private String kind;
    private String id;
    private String created;
    private String updated;
    private String summary;
    private GoogleCalendarDate start;
    private GoogleCalendarDate end;

}
