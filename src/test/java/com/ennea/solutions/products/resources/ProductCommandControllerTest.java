package com.ennea.solutions.products.resources;

import java.util.List;

import com.ennea.solutions.products.model.Product;
import com.ennea.solutions.products.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductCommandControllerTest {

    private static final String SUPPLIER = "ABBOTT INDIA LIMITED   HYDERABAD";
    private static final String PRODUCT_NAME = "CREMALAX 10MG TAB";

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /product/supplier - Found")
    void testGetProductBySupplierFound() throws Exception {
        // Given
        Product mockProduct = createProduct();
        doReturn(List.of(mockProduct)).when(productService).getProductsWithSupplier(Mockito.anyString(), Mockito.any(Pageable.class));

        // When
        mockMvc.perform(get("/products/{supplier}", SUPPLIER))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate the returned fields
                .andExpect(jsonPath("$.[0].supplier", is(SUPPLIER)))
                .andExpect(jsonPath("$.[0].name", is(PRODUCT_NAME)));
    }

    @Test
    @DisplayName("GET /product/supplier - Not Found")
    void testGetProductBySupplierNotFound() throws Exception {
        // Given
        doReturn(List.of()).when(productService).getProductsWithSupplier(Mockito.anyString(), Mockito.any(Pageable.class));

        // When
        mockMvc.perform(get("/products/{supplier}", SUPPLIER))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("GET /product/supplier?name=ProductName - Found")
    void testGetProductBySupplierAndNameFound() throws Exception {
        // Given
        Product mockProduct = createProduct();
        doReturn(List.of(mockProduct)).when(productService).getProductsWithSupplierAndName(Mockito.anyString(), Mockito.anyString(), Mockito.any(Pageable.class));

        // When
        mockMvc.perform(get("/products/{supplier}", SUPPLIER)
                        .param("name", PRODUCT_NAME)
                )

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate the returned fields
                .andExpect(jsonPath("$.[0].supplier", is(SUPPLIER)))
                .andExpect(jsonPath("$.[0].name", is(PRODUCT_NAME)));
    }

    @Test
    @DisplayName("GET /product/supplier?name=ProductName&ExpiredFalse - Found")
    void testGetProductBySupplierAndNameAndExpiredFalseFound() throws Exception {
        // Given
        Product mockProduct = createProduct();
        doReturn(List.of(mockProduct)).when(productService).getProductsWithSupplierAndNameAndExpired(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any(Pageable.class));

        // When
        mockMvc.perform(get("/products/{supplier}", SUPPLIER)
                        .param("name", PRODUCT_NAME)
                        .param("expired", "false")
                )

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate the returned fields
                .andExpect(jsonPath("$.[0].supplier", is(SUPPLIER)))
                .andExpect(jsonPath("$.[0].name", is(PRODUCT_NAME)));
    }

    @Test
    @DisplayName("GET /product/supplier?ExpiredFalse - Found")
    void testGetProductBySupplierAndExpiredFalseFound() throws Exception {
        // Given
        Product mockProduct = createProduct();
        doReturn(List.of(mockProduct)).when(productService).getProductsWithSupplierAndExpired(Mockito.anyString(), Mockito.any(), Mockito.any(Pageable.class));

        // When
        mockMvc.perform(get("/products/{supplier}", SUPPLIER)
                        .param("expired", "false")
                )

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate the returned fields
                .andExpect(jsonPath("$.[0].supplier", is(SUPPLIER)))
                .andExpect(jsonPath("$.[0].name", is(PRODUCT_NAME)));
    }

    private Product createProduct() {
        return Product.builder()
                .supplier(SUPPLIER)
                .name(PRODUCT_NAME)
                .build();
    }
}
