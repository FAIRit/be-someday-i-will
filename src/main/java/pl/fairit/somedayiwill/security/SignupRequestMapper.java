package pl.fairit.somedayiwill.security;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.fairit.somedayiwill.payload.SignUpRequest;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.UserDto;
import pl.fairit.somedayiwill.user.UserMapper;

@Mapper
public interface SignupRequestMapper {
    SignupRequestMapper INSTANCE = Mappers.getMapper(SignupRequestMapper.class);

    AppUser signupRequestToAppUser(SignUpRequest signUpRequest);
}
