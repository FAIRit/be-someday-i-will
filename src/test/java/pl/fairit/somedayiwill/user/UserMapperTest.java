package pl.fairit.somedayiwill.user;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void shouldMapUserToUserDtoDto() {
        AppUser user = AppUser.builder()
                .email("email@email.com")
                .name("Name")
                .password("jkdnkjasd")
                .createdAt(LocalDate.now())
                .id(570832493284L)
                .build();

        AppUserDto appUserDto = AppUserMapper.INSTANCE.map(user);

        assertThat(appUserDto).isNotNull();
        assertThat(appUserDto.getEmail()).isEqualTo("email@email.com");
        assertThat(appUserDto.getName()).isEqualTo("Name");
    }
}
