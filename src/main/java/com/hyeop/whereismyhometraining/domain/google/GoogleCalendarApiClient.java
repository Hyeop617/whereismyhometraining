package com.hyeop.whereismyhometraining.domain.google;

import com.hyeop.whereismyhometraining.domain.google.view.GoogleApiResponse;
import com.hyeop.whereismyhometraining.domain.google.view.GoogleCalendarEventDeleteErrorResponseDto;
import com.hyeop.whereismyhometraining.domain.google.view.GoogleCalendarEventInsertRequestDto;
import com.hyeop.whereismyhometraining.domain.google.view.GoogleCalendarEventResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "google-calendar-api",
        url = "${google.api.calendar.url}")
public interface GoogleCalendarApiClient {

    @GetMapping("/calendars/{email}/events")
    GoogleApiResponse getEvents(
            @PathVariable String email,
            @RequestParam(name = "access_token") String token
        );

    @PostMapping("/calendars/{email}/events")
    GoogleCalendarEventResult insertEvent(
            @PathVariable String email,
            @RequestParam(name = "access_token") String token,
            @RequestBody GoogleCalendarEventInsertRequestDto dto
            );

    @DeleteMapping("/calendars/{email}/events/{eventId}")
    GoogleCalendarEventDeleteErrorResponseDto deleteEvent(
            @PathVariable String email,
            @PathVariable String eventId,
            @RequestParam(name = "access_token") String token
    );
}
