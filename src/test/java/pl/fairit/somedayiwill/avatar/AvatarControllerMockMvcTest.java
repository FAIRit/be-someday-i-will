package pl.fairit.somedayiwill.avatar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class AvatarControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAvatarById() throws Exception {
        mockMvc.perform(get("/users/me/avatar"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        //what about auth?
    }
}
