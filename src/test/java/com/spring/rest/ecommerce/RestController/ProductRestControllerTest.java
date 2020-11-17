package com.spring.rest.ecommerce.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.ecommerce.entity.Product;
import com.spring.rest.ecommerce.repository.ProductRepository;
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
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

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
    void ShouldGetSingleProduct() throws Exception{
        // given
        Product testProduct = new Product();
        testProduct.setProductName("test");
        testProduct.setProductCategory("test");
        testProduct.setProductPrice(100);
        productRepository.save(testProduct);
        // when
        MvcResult mvcResult = mockMvc.perform(get("/product-api/products/" + testProduct.getProductID()))
                .andDo(print())
                .andExpect(status().is(200))
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
        // when
        MvcResult mvcResult = mockMvc.perform(get("/product-api/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        List products = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(products).isNotNull();
    }

    @Test
    @Transactional
    void ShouldCreateProduct() throws Exception{
        // given
        Product testProduct = new Product();
        testProduct.setProductName("test");
        testProduct.setProductCategory("test");
        testProduct.setProductPrice(100);
        // when
        MvcResult mvcResult = mockMvc.perform(post("/product-api/products")
                .content(objectMapper.writeValueAsString(testProduct))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn();
        // then
        Product product = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);
        assertThat(product).isNotNull();
        assertThat(product.getProductName()).isEqualTo(testProduct.getProductName());
        assertThat(product.getProductCategory()).isEqualTo(testProduct.getProductCategory());
        assertThat(product.getProductPrice()).isEqualTo(testProduct.getProductPrice());
    }

    @Test
    @Transactional
    void ShouldUpdateProduct() throws Exception{
        // given
        Product testProduct = new Product();
        testProduct.setProductName("test");
        testProduct.setProductCategory("test");
        testProduct.setProductPrice(100);
        productRepository.save(testProduct);
        testProduct.setProductName("change");
        testProduct.setProductCategory("change");
        testProduct.setProductPrice(200);
        // when
        MvcResult mvcResult = mockMvc.perform(put("/product-api/products")
                .content(objectMapper.writeValueAsString(testProduct))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.ACCEPTED.value()))
                .andReturn();
        // then
        Product product = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);
        assertThat(product).isNotNull();
        assertThat(product.getProductID()).isEqualTo(testProduct.getProductID());
        assertThat(product.getProductName()).isEqualTo(testProduct.getProductName());
        assertThat(product.getProductCategory()).isEqualTo(testProduct.getProductCategory());
        assertThat(product.getProductPrice()).isEqualTo(testProduct.getProductPrice());
    }

    @Test
    @Transactional
    void ShouldDeleteProductById() throws Exception{
        // given
        Product testProduct = new Product();
        testProduct.setProductName("test");
        testProduct.setProductCategory("test");
        testProduct.setProductPrice(100);
        productRepository.save(testProduct);
        // when
        MvcResult mvcResult = mockMvc.perform(delete("/product-api/products/" + testProduct.getProductID()))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        // then
        assertThat(productRepository.findAllById(Collections.singleton(testProduct.getProductID())).isEmpty()).isTrue();
    }
}