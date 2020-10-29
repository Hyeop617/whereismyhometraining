package com.hyeop.whereismyhometraining.mapper;

//import com.hyeop.whereismyhometraining.config.oauth2.dto.OAuthAttributes;

import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.account.dto.ProfileEditResponseDto;
import com.hyeop.whereismyhometraining.entity.account.dto.SignupRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "sns", defaultValue = "NONE")
    Account toAccount(SignupRequestDto dto);

    ProfileEditResponseDto toProfileEditResponseDto(Account account);
}
