package com.spring.rest.ecommerce.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.ecommerce.entity.*;
import com.spring.rest.ecommerce.repository.OrderRepository;
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
import java.time.LocalDate;
import java.util.ArrayList;
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
    void shouldReturnAllMyOrders() throws Exception{
        // given
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
        assertThat(response.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnMyOrderById() throws Exception{
        // given
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

        mockMvc.perform(get("/user/orders/" + testOrder.getOrderId() * 2)
                .header("Authorization", token)
        )
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();

        MvcResult result = mockMvc.perform(get("/user/orders/" + testOrder.getOrderId())
                .header("Authorization", token)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        Order response = objectMapper.readValue(result.getResponse().getContentAsString(), Order.class);
        assertThat(response).isNotNull();
        assertThat(response.getOrderDate()).isEqualTo(date);
        assertThat(response.getOrderId()).isEqualTo(testOrder.getOrderId());
    }

    @Test
    void shouldCreateOrder() throws Exception{
        // given
        List<Product> testListOfProducts = new ArrayList<>();
        testListOfProducts.add(new Product("test", "test", 100));
        testListOfProducts.add(new Product("test1", "test1", 200));
        testListOfProducts.add(new Product("test2", "test2", 300));
        // when
        MvcResult login = mockMvc.perform(post("/login")
                .content("{\"username\" : \"test_test\", \"password\" : \"test\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        String token = login.getResponse().getHeader("Authorization");
        MvcResult result = mockMvc.perform(post("/user/orders")
                .content(objectMapper.writeValueAsString(testListOfProducts))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        // then
        ResponseMessage responseMessage = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseMessage.class);
        assertThat(responseMessage).isNotNull();
        assertThat(responseMessage.getMessage().contains("New resource created with id:")).isTrue();
    }
}