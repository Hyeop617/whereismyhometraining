package com.hyeop.whereismyhometraining.domain.common;

import com.google.api.services.calendar.CalendarScopes;
import com.hyeop.whereismyhometraining.domain.google.GoogleCalendarApiClient;
import com.hyeop.whereismyhometraining.domain.google.view.GoogleApiResponse;
import com.hyeop.whereismyhometraining.domain.google.view.GoogleCalendarEventDeleteErrorResponseDto;
import com.hyeop.whereismyhometraining.domain.google.view.GoogleCalendarEventInsertRequestDto;
import com.hyeop.whereismyhometraining.domain.google.view.GoogleCalendarEventResult;
import com.hyeop.whereismyhometraining.entity.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
public class GoogleCalendarUtil {

    @Autowired
    private GoogleCalendarApiClient googleCalendarApiClient;

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    public GoogleApiResponse getEvents(Account account){
        String email = account.getEmail();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient google = oAuth2AuthorizedClientService.loadAuthorizedClient("google", authentication.getName());
        OAuth2AccessToken accessToken = google.getAccessToken();
        String tokenValue = accessToken.getTokenValue();
        return googleCalendarApiClient.getEvents(email, tokenValue);
    }

    public GoogleCalendarEventResult insertEvent(Account account, GoogleCalendarEventInsertRequestDto dto){
        String email = account.getEmail();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient google = oAuth2AuthorizedClientService.loadAuthorizedClient("google", authentication.getName());
        OAuth2AccessToken accessToken = google.getAccessToken();
        String tokenValue = accessToken.getTokenValue();
        return googleCalendarApiClient.insertEvent(email, tokenValue, dto);
    }

    public GoogleCalendarEventDeleteErrorResponseDto deleteEvent(Account account, String eventId){
        String email = account.getEmail();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient google = oAuth2AuthorizedClientService.loadAuthorizedClient("google", authentication.getName());
        OAuth2AccessToken accessToken = google.getAccessToken();
        String tokenValue = accessToken.getTokenValue();
        return googleCalendarApiClient.deleteEvent(email, eventId, tokenValue);
    }
}
