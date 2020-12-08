package com.spring.rest.ecommerce.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.ecommerce.entity.Order;
import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.entity.UserAuthority;
import com.spring.rest.ecommerce.entity.UserDetail;
import com.spring.rest.ecommerce.repository.OrderRepository;
import com.spring.rest.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserOrdersRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

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
    @Transactional
    void getMyOrders() throws Exception{
        // when
        LocalDate date = LocalDate.now();
        Order testOrder = new Order(date, testUser, null);
        orderRepository.save(testOrder);
        // when
        MvcResult login = mockMvc.perform(post("/login")
                    .content("{\"username\" : \"test_test\", \"password\" : \"test\"}"))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andReturn();

        String token = login.getResponse().getHeader("Authorization");
        MvcResult result = mockMvc.perform(get("/user/orders")
                .header("Authorization", token)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        List response = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(response).isNotNull();
    }

    @Test
    void getMyOrderById() {
        // given
        // when
        // then
    }

    @Test
    void createOrder() {
        // given
        // when
        // then
    }
}