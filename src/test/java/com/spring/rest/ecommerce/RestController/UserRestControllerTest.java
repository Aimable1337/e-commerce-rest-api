package com.spring.rest.ecommerce.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.ecommerce.DTO.User.NewPasswordDTO;
import com.spring.rest.ecommerce.DTO.User.NewUserNameDTO;
import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.entity.UserAuthority;
import com.spring.rest.ecommerce.entity.UserDetail;
import com.spring.rest.ecommerce.repository.UserRepository;
import com.spring.rest.ecommerce.response.ResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private boolean setUpIsDone = false;

    private User testUser;

    @BeforeEach
    void init() {
        if (setUpIsDone){
            return;
        }
        testUser = new User("test_test", "{bcrypt}$2y$12$KTim0KiUmdqXnrCKUK5Bd.WvSm0JROE8qy14cxzJ5y1j6GFrisaFS", true,
                new UserDetail("test", "test", "test", "test", "test","test", "test"),
                new UserAuthority("test_test", "ROLE_USER")
        );
        userRepository.save(testUser);
        setUpIsDone = true;
    }

    @Test
    void shouldReturnMyDetails() throws Exception{
        // given
        MvcResult login = mockMvc.perform(post("/login")
                .content("{\"username\" : \"test_test\", \"password\" : \"test\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String token = login.getResponse().getHeader("Authorization");
        // when
        MvcResult result = mockMvc.perform(get("/account/show-details")
                .header("Authorization", token)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        UserDetail response = objectMapper.readValue(result.getResponse().getContentAsString(), UserDetail.class);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(testUser.getUserDetails().getId());
        assertThat(response.getAddress()).isEqualTo(testUser.getUserDetails().getAddress());
    }

    @Test
    @Transactional
    void shouldChangeMyUserName() throws Exception{
        // given
        MvcResult login = mockMvc.perform(post("/login")
                .content("{\"username\" : \"test_test\", \"password\" : \"test\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String token = login.getResponse().getHeader("Authorization");
        NewUserNameDTO newUserName = new NewUserNameDTO("newUserName");
        // when
        MvcResult result = mockMvc.perform(put("/account/change-username")
                .content(objectMapper.writeValueAsString(newUserName))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        )
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        // then
        ResponseMessage response = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseMessage.class);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Resource with id: " + testUser.getUserId() + " updated");
    }

    @Test
    @Transactional
    void shouldChangeMyPassword() throws Exception{
        // given
        MvcResult login = mockMvc.perform(post("/login")
                .content("{\"username\" : \"test_test\", \"password\" : \"test\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String token = login.getResponse().getHeader("Authorization");
        NewPasswordDTO newPasswordDTO = new NewPasswordDTO("newPassword", "test");
        // when
        MvcResult result = mockMvc.perform(put("/account/change-password")
                .content(objectMapper.writeValueAsString(newPasswordDTO))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        )
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        // then
        mockMvc.perform(post("/login")
                .content("{\"username\" : \"test_test\", \"password\" : \"test\"}"))
                .andDo(print())
                .andExpect(status().is(401))
                .andReturn();
        mockMvc.perform(post("/login")
                .content("{\"username\" : \"test_test\", \"password\" : \"newPassword\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        ResponseMessage response = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseMessage.class);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Resource with id: " + testUser.getUserId() + " updated");
    }

    @Test
    @Transactional
    void changeMyDetails() throws Exception{
        // given
        MvcResult login = mockMvc.perform(post("/login")
                .content("{\"username\" : \"test_test\", \"password\" : \"test\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String token = login.getResponse().getHeader("Authorization");
        UserDetail newUserDetail = new UserDetail("change", "change", "change", "change", "change","change", "test");
        // when
        MvcResult result = mockMvc.perform(put("/account/change-details")
                .content(objectMapper.writeValueAsString(newUserDetail))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        )
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        // then
        ResponseMessage response = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseMessage.class);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Resource with id: " + testUser.getUserId() + " updated");

        MvcResult resultAfterUpdate = mockMvc.perform(get("/account/show-details")
                .header("Authorization", token)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        UserDetail responseAfterUpdate = objectMapper.readValue(resultAfterUpdate.getResponse().getContentAsString(), UserDetail.class);
        assertThat(responseAfterUpdate).isNotNull();
        assertThat(responseAfterUpdate.getAddress()).isEqualTo(newUserDetail.getAddress());
        assertThat(responseAfterUpdate.getCity()).isEqualTo(newUserDetail.getCity());
    }
}