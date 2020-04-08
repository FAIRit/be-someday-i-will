package pl.fairit.somedayiwill.security.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.fairit.somedayiwill.user.AppUser;

@Mapper
public interface SignupRequestMapper {
    SignupRequestMapper INSTANCE = Mappers.getMapper(SignupRequestMapper.class);

    AppUser map(final SignUpRequest signUpRequest);
}
