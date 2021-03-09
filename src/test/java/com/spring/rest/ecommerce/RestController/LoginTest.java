package com.spring.rest.ecommerce.RestController;

import com.spring.rest.ecommerce.entity.User;

import com.spring.rest.ecommerce.entity.UserAuthority;
import com.spring.rest.ecommerce.entity.UserDetail;
import com.spring.rest.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init(){
        User testUser = new User("test", "{bcrypt}$2y$12$KTim0KiUmdqXnrCKUK5Bd.WvSm0JROE8qy14cxzJ5y1j6GFrisaFS", true,
                new UserDetail("test", "test", "test", "test", "test","test", "test"),
                new UserAuthority("test", "ROLE_USER")
        );
        userRepository.save(testUser);
    }

    @Test
    void LoginTestWithBadPassword() throws Exception{
        mockMvc.perform(post("/login")
                .content("{\"username\" : \"test2\", \"password\" : \"badpassword\"}"))
                .andDo(print())
                .andExpect(status().is(401))
                .andReturn();
    }

    @Test
    void LoginTestWithCorrectPassword() throws Exception{
        mockMvc.perform(post("/login")
                .content("{\"username\" : \"test\", \"password\" : \"test\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }
}
