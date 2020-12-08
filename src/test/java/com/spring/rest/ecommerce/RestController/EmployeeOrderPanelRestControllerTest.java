package com.spring.rest.ecommerce.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.ecommerce.entity.Order;

import com.spring.rest.ecommerce.entity.User;
import com.spring.rest.ecommerce.repository.OrderRepository;
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
@WithMockUser(roles = "EMPLOYEE")
class EmployeeOrderPanelRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ResponseMessageGenerator responseMessageGenerator;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void shouldGetAllOrders() throws Exception{
        // given
        Order testOrder = new Order(LocalDate.now(), new User(), new ArrayList<>());
        orderRepository.save(testOrder);
        // when
        MvcResult result = mockMvc.perform(get("/employee/order-panel/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        List orders = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(orders).isNotNull();
    }

    @Test
    @Transactional
    void shouldGetOrderById() throws Exception{
        // given
        Order testOrder = new Order(LocalDate.now(), new User(), new ArrayList<>());
        orderRepository.save(testOrder);
        // when
        MvcResult result = mockMvc.perform(get("/employee/order-panel/orders/" + testOrder.getOrderId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        Order order = objectMapper.readValue(result.getResponse().getContentAsString(), Order.class);
        assertThat(order).isNotNull();
        assertThat(testOrder.getOrderDate()).isEqualTo(order.getOrderDate());
    }

    @Test
    @Transactional
    void shouldCreateOrder() throws Exception{
        // given
        Order testOrder = new Order(LocalDate.now(), new User(), new ArrayList<>());
        // when
        MvcResult result = mockMvc.perform(post("/employee/order-panel/orders")
                .content(objectMapper.writeValueAsString(testOrder))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        // then
        Order order = objectMapper.readValue(result.getResponse().getContentAsString(), Order.class);
        assertThat(order).isNotNull();
        assertThat(testOrder.getOrderDate()).isEqualTo(order.getOrderDate());
    }

    @Test
    @Transactional
    void shouldUpdateOrder() throws Exception{
        // given
        Order testOrder = new Order(LocalDate.now(), new User(), new ArrayList<>());
        orderRepository.save(testOrder);
        testOrder.setOrderDate(LocalDate.MIN);
        // when
        MvcResult result = mockMvc.perform(put("/employee/order-panel/orders")
                .content(objectMapper.writeValueAsString(testOrder))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        // then
        ResponseMessage responseMessage = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseMessage.class);
        assertThat(responseMessage).isNotNull();
        assertThat(responseMessage.getMessage()).isEqualTo(responseMessageGenerator.getResponseForSuccessPutMethod(testOrder.getOrderId()).getMessage());
    }

    @Test
    @Transactional
    void deleteOrder() throws Exception{
        // given
        Order testOrder = new Order(LocalDate.now(), new User(), new ArrayList<>());
        orderRepository.save(testOrder);
        // when
        MvcResult result = mockMvc.perform(delete("/employee/order-panel/orders/" + testOrder.getOrderId()))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        // then
        ResponseMessage responseMessage = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseMessage.class);
        assertThat(responseMessage).isNotNull();
        assertThat(responseMessage.getMessage()).isEqualTo(responseMessageGenerator.getResponseForSuccessDeleteMethod(testOrder.getOrderId()).getMessage());
    }
}