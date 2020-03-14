package pl.fairit.somedayiwill.user;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void shouldMapUserToUserDtoDto() {
        //given
        var faker = new Faker();
        var user = AppUser.builder()
                .email("fake@email.com")
                .name(faker.name().firstName())
                .password("jkdnkjasd")
                .createdAt(LocalDate.now())
                .id(faker.number().randomNumber())
                .build();
        //when
        var appUserDto = AppUserMapper.INSTANCE.map(user);
        //then
        assertThat(appUserDto).isNotNull();
        assertThat(appUserDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(appUserDto.getName()).isEqualTo(user.getName());
    }
}
