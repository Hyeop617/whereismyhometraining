package com.hyeop.whereismyhometraining.mapper;

//import com.hyeop.whereismyhometraining.config.oauth2.dto.OAuthAttributes;
import com.hyeop.whereismyhometraining.entity.account.Account;
import com.hyeop.whereismyhometraining.entity.account.dto.SignupRequestDto;
import com.hyeop.whereismyhometraining.entity.accountSns.AccountSns;
import com.hyeop.whereismyhometraining.entity.accountSns.dto.SignupSnsRequestDto;
import com.hyeop.whereismyhometraining.entity.enums.Sns;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "role", constant = "USER")
    Account toAccount(SignupRequestDto dto);

    @Mapping(target = "role", constant = "USER")
    AccountSns toAccount(SignupSnsRequestDto dto);
//
//    @Mapping(target = "role", defaultValue = "USER")
//    @Mapping(target = "sns", defaultValue = "GOOGLE")
//    Account googleToAccount(OAuthAttributes oAuthAttributes);
}
