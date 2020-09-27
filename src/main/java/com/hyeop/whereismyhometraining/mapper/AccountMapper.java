package com.hyeop.whereismyhometraining.mapper;

import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.account.dto.SignupRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(target = "sns", defaultValue = "NONE")
    Account toAccount(SignupRequestDto dto);
}
