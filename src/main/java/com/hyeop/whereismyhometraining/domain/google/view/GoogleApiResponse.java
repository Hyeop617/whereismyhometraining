package com.hyeop.whereismyhometraining.domain.google.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GoogleApiResponse {

    public String kind;
    public String summary;
    public String updated;
    public String timeZone;
    public List<GoogleCalendarEventResult> items;
}
