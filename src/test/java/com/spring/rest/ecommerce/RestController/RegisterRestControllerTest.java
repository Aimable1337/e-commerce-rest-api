package com.spring.rest.ecommerce.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.entity.UserAuthority;
import com.spring.rest.ecommerce.entity.UserDetail;
import com.spring.rest.ecommerce.repository.UserRepository;
import com.spring.rest.ecommerce.response.ResponseMessage;
import com.spring.rest.ecommerce.response.ResponseMessageGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void shouldCreateNewUser() throws Exception{
        // given
        User testUser = new User("test_test", "{bcrypt}$2y$12$KTim0KiUmdqXnrCKUK5Bd.WvSm0JROE8qy14cxzJ5y1j6GFrisaFS", true,
                new UserDetail("test", "test", "test", "test", "test","test", "test"),
                new UserAuthority("test_test", "ROLE_USER")
        );
        // when
        MvcResult mvcResult = mockMvc.perform(post("/register")
                .content(objectMapper.writeValueAsString(testUser))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        // then
        ResponseMessage response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);
        User createdUser = userRepository.findByUserName("test_test").get();
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo(responseMessageGenerator.getResponseForSuccessPostMethod(createdUser.getUserId()).getMessage());
    }
}