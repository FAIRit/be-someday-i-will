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

        UserDto userDto = UserMapper.INSTANCE.userToUserDto(user);

        assertThat(userDto).isNotNull();
        assertThat(userDto.getEmail()).isEqualTo("email@email.com");
        assertThat(userDto.getName()).isEqualTo("Name");
    }
}
