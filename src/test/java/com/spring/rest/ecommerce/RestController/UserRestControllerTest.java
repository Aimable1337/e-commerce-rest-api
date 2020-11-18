package com.spring.rest.ecommerce.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.ecommerce.entity.UserDetails;
import com.spring.rest.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void LoginTestWithBadPassword() throws Exception{
        mockMvc.perform(post("/login")
                .content("{\"username\" : \"test2\", \"password\" : \"badpassword\"}")
        )
                .andDo(print())
                .andExpect(status().is(401))
                .andReturn();
    }

    @Test
    void LoginTest() throws Exception{
        mockMvc.perform(post("/login")
                .content("{\"username\" : \"test2\", \"password\" : \"test1\"}")
        )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    @Transactional
    void ShouldGetSingleUser() throws Exception{
        // given
        UserDetails testUser = new UserDetails();
        testUser.setFirstName("test");
        testUser.setLastName("test");
        testUser.setEmail("test@test.pl");
        userRepository.save(testUser);
        // when
        MvcResult mvcResult = mockMvc.perform(get("/user-api/users/" + testUser.getUserID()))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        // then
        UserDetails user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDetails.class);
        assertThat(user).isNotNull();
        assertThat(user.getUserID()).isEqualTo(testUser.getUserID());
        assertThat(user.getFirstName()).isEqualTo(testUser.getFirstName());
        assertThat(user.getLastName()).isEqualTo(testUser.getLastName());
        assertThat(user.getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    @Transactional
    void ShouldGetAllUsers() throws Exception{
        // given
        // when
        MvcResult mvcResult = mockMvc.perform(get("/user-api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        List users = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(users).isNotNull();
    }

    @Test
    @Transactional
    void ShouldCreateUser() throws Exception{
        // given
        UserDetails testUser = new UserDetails();
        testUser.setFirstName("test");
        testUser.setLastName("test");
        testUser.setEmail("test@test.pl");
        // when
        MvcResult mvcResult = mockMvc.perform(post("/user-api/users")
                .content(objectMapper.writeValueAsString(testUser))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn();
        // then
        UserDetails user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDetails.class);
        assertThat(user).isNotNull();
        assertThat(user.getFirstName()).isEqualTo(testUser.getFirstName());
        assertThat(user.getLastName()).isEqualTo(testUser.getLastName());
        assertThat(user.getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    @Transactional
    void ShouldUpdateUser() throws Exception{
        // given
        UserDetails testUser = new UserDetails();
        testUser.setFirstName("test");
        testUser.setLastName("test");
        testUser.setEmail("test@test.pl");
        userRepository.save(testUser);
        testUser.setFirstName("change");
        testUser.setLastName("change");
        testUser.setEmail("change@test.pl");
        // when
        MvcResult mvcResult = mockMvc.perform(put("/user-api/users")
                .content(objectMapper.writeValueAsString(testUser))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.ACCEPTED.value()))
                .andReturn();
        // then
        UserDetails user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDetails.class);
        assertThat(user).isNotNull();
        assertThat(user.getUserID()).isEqualTo(testUser.getUserID());
        assertThat(user.getFirstName()).isEqualTo(testUser.getFirstName());
        assertThat(user.getLastName()).isEqualTo(testUser.getLastName());
        assertThat(user.getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    @Transactional
    void ShouldDeleteUserById() throws Exception{
        // given
        UserDetails testUser = new UserDetails();
        testUser.setFirstName("test");
        testUser.setLastName("test");
        testUser.setEmail("test@test.pl");
        userRepository.save(testUser);
        // when
        mockMvc.perform(delete("/user-api/users/" + testUser.getUserID()))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        // then
        assertThat(userRepository.findAllById(Collections.singleton(testUser.getUserID())).isEmpty()).isTrue();
    }
}