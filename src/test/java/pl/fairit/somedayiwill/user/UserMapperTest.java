package pl.fairit.somedayiwill.user;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void shouldMapUserToUserDtoDto() {
        var faker = new Faker();
        var user = AppUser.builder()
                .email(faker.internet().emailAddress())
                .name(faker.name().firstName())
                .password(faker.internet().password())
                .createdAt(LocalDate.now())
                .id(faker.number().randomNumber())
                .build();

        var appUserDto = AppUserMapper.INSTANCE.map(user);

        assertThat(appUserDto).isNotNull();
        assertThat(appUserDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(appUserDto.getName()).isEqualTo(user.getName());
    }
}
