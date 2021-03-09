package com.spring.rest.ecommerce.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.ecommerce.DTO.User.UserViewDTO;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
class AdminUserPanelRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void shouldGetAllUsers() throws Exception{
        // given
        User testUser = new User("test_test", "{bcrypt}$2y$12$KTim0KiUmdqXnrCKUK5Bd.WvSm0JROE8qy14cxzJ5y1j6GFrisaFS", true,
                new UserDetail("test", "test", "test", "test", "test","test", "test"),
                new UserAuthority("test_test", "ROLE_USER")
        );
        userRepository.save(testUser);
        // when
        MvcResult mvcResult = mockMvc.perform(get("/admin/user-panel/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        List users = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(users).isNotNull();
    }

    @Test
    @Transactional
    void shouldGetSingleUserById() throws Exception {
        // given
        User testUser = new User("test_test", "{bcrypt}$2y$12$KTim0KiUmdqXnrCKUK5Bd.WvSm0JROE8qy14cxzJ5y1j6GFrisaFS", true,
                new UserDetail("test", "test", "test", "test", "test","test", "test"),
                new UserAuthority("test_test", "ROLE_USER")
        );
        userRepository.save(testUser);
        // when
        MvcResult mvcResult = mockMvc.perform(get("/admin/user-panel/users/id=" + testUser.getUserId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        UserViewDTO user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserViewDTO.class);
        assertThat(user).isNotNull();
//        assertThat(user.getUserName()).isEqualTo(testUser.getUserName());
//        assertThat(user.getUserId()).isEqualTo(testUser.getUserId());
    }

    @Test
    @Transactional
    void getUserByUserName() throws Exception {
        // given
        User testUser = new User("test_test", "{bcrypt}$2y$12$KTim0KiUmdqXnrCKUK5Bd.WvSm0JROE8qy14cxzJ5y1j6GFrisaFS", true,
                new UserDetail("test", "test", "test", "test", "test","test", "test"),
                new UserAuthority("test_test", "ROLE_USER")
        );
        userRepository.save(testUser);
        // when
        MvcResult mvcResult = mockMvc.perform(get("/admin/user-panel/users/name=" + testUser.getUserName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        UserViewDTO user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserViewDTO.class);
        assertThat(user).isNotNull();
//        assertThat(user.getUserName()).isEqualTo(testUser.getUserName());
//        assertThat(user.getUserId()).isEqualTo(testUser.getUserId());
    }

    @Test
    @Transactional
    void shouldCreateUser() throws Exception{
        // given
        User testUser = new User("test_test", "{bcrypt}$2y$12$KTim0KiUmdqXnrCKUK5Bd.WvSm0JROE8qy14cxzJ5y1j6GFrisaFS", true,
                new UserDetail("test", "test", "test", "test", "test","test", "test"),
                new UserAuthority("test_test", "ROLE_USER")
        );
        // when
        MvcResult mvcResult = mockMvc.perform(post("/admin/user-panel/users")
                .content(objectMapper.writeValueAsString(testUser))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        // then
        User user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isEqualTo(testUser.getUserName());
    }

    @Test
    @Transactional
    void updateUser() throws Exception {
        // given
        User testUser = new User("test_test", "{bcrypt}$2y$12$KTim0KiUmdqXnrCKUK5Bd.WvSm0JROE8qy14cxzJ5y1j6GFrisaFS", true,
                new UserDetail("test", "test", "test", "test", "test","test", "test"),
                new UserAuthority("test_test", "ROLE_USER")
        );
        userRepository.save(testUser);
        testUser.setPassword("change");
        testUser.setUserName("change");
        // when
        MvcResult mvcResult = mockMvc.perform(put("/admin/user-panel/users")
                .content(objectMapper.writeValueAsString(testUser))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        // then
        User user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isNotEqualTo("update_test");
        assertThat(user.getPassword()).isNotEqualTo("test");
    }

    @Test
    @Transactional
    void shouldChangeAuthority() throws Exception{
        // given
        User testUser = new User("test_test", "{bcrypt}$2y$12$KTim0KiUmdqXnrCKUK5Bd.WvSm0JROE8qy14cxzJ5y1j6GFrisaFS", true,
                new UserDetail("test", "test", "test", "test", "test","test", "test"),
                new UserAuthority("test_test", "ROLE_USER")
        );
        userRepository.save(testUser);

        UserAuthority newAuthority = new UserAuthority(testUser.getUserName(), "ROLE_EMPLOYEE");
        newAuthority.setId(testUser.getUserAuthority().getId());
        // when
        MvcResult mvcResult = mockMvc.perform(put("/admin/user-panel/change-authority/" + testUser.getUserId())
                .content(objectMapper.writeValueAsString(newAuthority))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        // then
        ResponseMessage response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo(responseMessageGenerator.getResponseForSuccessPutMethod(testUser.getUserId()).getMessage());
    }

    @Test
    @Transactional
    void shouldBanUser() throws Exception{
        // given
        User testUser = new User("test_test", "{bcrypt}$2y$12$KTim0KiUmdqXnrCKUK5Bd.WvSm0JROE8qy14cxzJ5y1j6GFrisaFS", true,
                new UserDetail("test", "test", "test", "test", "test","test", "test"),
                new UserAuthority("test_test", "ROLE_USER")
        );
        userRepository.save(testUser);
        // when
        MvcResult mvcResult = mockMvc.perform(put("/admin/user-panel/ban-user/" + testUser.getUserId()))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        // then
        ResponseMessage response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);
        mockMvc.perform(post("/login")
                .content("{\"username\" : \"ban_test\", \"password\" : \"test\"}"))
                .andDo(print())
                .andExpect(status().is(401))
                .andReturn();
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo(responseMessageGenerator.getResponseForSuccessBanMethod(testUser.getUserId()).getMessage());
    }

    @Test
    @Transactional
    void shouldDeleteUser() throws Exception{
        // given
        User testUser = new User("test_test", "{bcrypt}$2y$12$KTim0KiUmdqXnrCKUK5Bd.WvSm0JROE8qy14cxzJ5y1j6GFrisaFS", true,
                new UserDetail("test", "test", "test", "test", "test","test", "test"),
                new UserAuthority("test_test", "ROLE_USER")
        );
        userRepository.save(testUser);
        // when
        mockMvc.perform(delete("/admin/user-panel/users/" + testUser.getUserId()))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        entityManager.flush();
        // then
        MvcResult result = mockMvc.perform(get("/admin/user-panel/users/id=" + testUser.getUserId()))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();
    }
}