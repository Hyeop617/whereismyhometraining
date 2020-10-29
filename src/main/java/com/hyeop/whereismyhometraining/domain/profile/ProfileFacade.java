package com.hyeop.whereismyhometraining.domain.profile;

import com.hyeop.whereismyhometraining.entity.account.dto.ProfileEditRequestDto;
import com.hyeop.whereismyhometraining.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileFacade {

    @Autowired
    private ProfileService profileService;

    public ResponseResult getAccount() {
        return profileService.getAccount();
    }

    public ResponseEntity edit(ProfileEditRequestDto dto) {
        return profileService.edit(dto);
    }

    public ResponseEntity check(String password) {
        return profileService.check(password);
    }
}
