package com.spring.rest.ecommerce.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void ShouldGetSingleProduct() throws Exception{
        // given
        Product testProduct = new Product("test", "test",100);
        productRepository.save(testProduct);
        // when
        MvcResult mvcResult = mockMvc.perform(get("/products/" + testProduct.getProductID()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        Product product = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);
        assertThat(product).isNotNull();
        assertThat(product.getProductID()).isEqualTo(testProduct.getProductID());
        assertThat(product.getProductName()).isEqualTo(testProduct.getProductName());
        assertThat(product.getProductCategory()).isEqualTo(testProduct.getProductCategory());
    }

    @Test
    @Transactional
    void ShouldGetAllProducts() throws Exception{
        // given
        Product testProduct = new Product("test", "test",100);
        productRepository.save(testProduct);
        // when
        MvcResult mvcResult = mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        List products = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(products).isNotNull();
    }
}