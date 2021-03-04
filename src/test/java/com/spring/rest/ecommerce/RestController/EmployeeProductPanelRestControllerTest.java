package com.spring.rest.ecommerce.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.repository.ProductRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "EMPLOYEE")
class EmployeeProductPanelRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResponseMessageGenerator responseMessageGenerator;

    @Test
    @Transactional
    void createProduct() throws Exception{
        // given
        Product testProduct = new Product("test", "test", 100);
        // when
        MvcResult result = mockMvc.perform(post("/employee/products")
                .content(objectMapper.writeValueAsString(testProduct))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        // then
        ResponseMessage response = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseMessage.class);
        assertThat(response).isNotNull();
    }

    @Test
    @Transactional
    void updateProduct() throws Exception{
        // given
        Product testProduct = new Product("test", "test", 100);
        productRepository.save(testProduct);
        testProduct.setProductName("change");
        // when
        MvcResult result = mockMvc.perform(put("/employee/products")
                .content(objectMapper.writeValueAsString(testProduct))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        // then
        ResponseMessage response = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseMessage.class);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo(responseMessageGenerator.getResponseForSuccessPutMethod(testProduct.getProductID()).getMessage());
    }

    @Test
    @Transactional
    void deleteProductById() throws Exception{
        // given
        Product testProduct = new Product("test", "test", 100);
        productRepository.save(testProduct);
        // when
        MvcResult result = mockMvc.perform(delete("/employee/products/" + testProduct.getProductID()))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        // then
        ResponseMessage response = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseMessage.class);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo(responseMessageGenerator.getResponseForSuccessDeleteMethod(testProduct.getProductID()).getMessage());
    }
}