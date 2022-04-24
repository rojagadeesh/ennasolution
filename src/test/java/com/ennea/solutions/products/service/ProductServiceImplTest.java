package com.ennea.solutions.products.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.ennea.solutions.products.entity.ProductEntity;
import com.ennea.solutions.products.model.Product;
import com.ennea.solutions.products.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    private static final String SUPPLIER = "ABBOTT INDIA LIMITED   HYDERABAD";
    private static final String PRODUCT_NAME = "CREMALAX 10MG TAB";

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void testShouldBeAbleToRetrieveProductsForGivenSupplier() {
        // Given
        Pageable pageable = PageRequest.of(0, 1);
        ProductEntity product = createProduct();
        Mockito.when(productRepository.findBySupplierAndStockGreaterThan(Mockito.anyString(), Mockito.anyInt(), Mockito.any()))
                .thenReturn(List.of(product));
        // When
        List<Product> productList = productService.getProductsWithSupplier(SUPPLIER, pageable);

        // Then
        Assertions.assertNotNull(productList);
        Assertions.assertEquals(1, productList.size());
        Assertions.assertEquals(SUPPLIER, productList.get(0).getSupplier());
        Mockito.verify(productRepository, Mockito.times(1)).findBySupplierAndStockGreaterThan(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
    }

    @Test
    void testShouldBeAbleToRetrieveProductsForGivenSupplierAndName() {
        // Given
        Pageable pageable = PageRequest.of(0, 1);
        ProductEntity product = createProduct();
        Mockito.when(productRepository.findBySupplierAndNameAndStockGreaterThan(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.any()))
                .thenReturn(List.of(product));
        // When
        List<Product> productList = productService.getProductsWithSupplierAndName(SUPPLIER, PRODUCT_NAME, pageable);

        // Then
        Assertions.assertNotNull(productList);
        Assertions.assertEquals(1, productList.size());
        Assertions.assertEquals(SUPPLIER, productList.get(0).getSupplier());
        Mockito.verify(productRepository, Mockito.times(1)).findBySupplierAndNameAndStockGreaterThan(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.any());

    }

    @Test
    void testShouldBeAbleToRetrieveProductsForGivenSupplierAndExpiredFalse() {

        // Given
        Pageable pageable = PageRequest.of(0, 1);
        ProductEntity product = createProduct();
        Mockito.when(productRepository.findBySupplierAndExpGreaterThanAndStockGreaterThan(Mockito.anyString(), Mockito.any(), Mockito.anyInt(), Mockito.any()))
                .thenReturn(List.of(product));
        // When
        List<Product> productList = productService.getProductsWithSupplierAndExpired(SUPPLIER, Boolean.FALSE, pageable);

        // Then
        Assertions.assertNotNull(productList);
        Assertions.assertEquals(1, productList.size());
        Assertions.assertEquals(SUPPLIER, productList.get(0).getSupplier());
        Mockito.verify(productRepository, Mockito.times(1)).findBySupplierAndExpGreaterThanAndStockGreaterThan(Mockito.anyString(), Mockito.any(), Mockito.anyInt(), Mockito.any());
    }

    @Test
    void testShouldBeAbleToRetrieveProductsForGivenSupplierAndExpiredTrue() {

        // Given
        Pageable pageable = PageRequest.of(0, 1);
        ProductEntity product = createProduct();
        Mockito.when(productRepository.findBySupplierAndStockGreaterThan(Mockito.anyString(), Mockito.anyInt(), Mockito.any()))
                .thenReturn(List.of(product));
        // When
        List<Product> productList = productService.getProductsWithSupplierAndExpired(SUPPLIER, Boolean.TRUE, pageable);

        // Then
        Assertions.assertNotNull(productList);
        Assertions.assertEquals(1, productList.size());
        Assertions.assertEquals(SUPPLIER, productList.get(0).getSupplier());
        Mockito.verify(productRepository, Mockito.times(1)).findBySupplierAndStockGreaterThan(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
    }

    @Test
    void testShouldBeAbleToRetrieveProductsForGivenSupplierAndProductNameAndExpiredFalse() {

        // Given
        Pageable pageable = PageRequest.of(0, 1);
        ProductEntity product = createProduct();
        Mockito.when(productRepository.findBySupplierAndNameAndExpGreaterThanAndStockGreaterThan(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.anyInt(), Mockito.any()))
                .thenReturn(List.of(product));
        // When
        List<Product> productList = productService.getProductsWithSupplierAndNameAndExpired(SUPPLIER, PRODUCT_NAME, Boolean.FALSE, pageable);

        // Then
        Assertions.assertNotNull(productList);
        Assertions.assertEquals(1, productList.size());
        Assertions.assertEquals(SUPPLIER, productList.get(0).getSupplier());
        Mockito.verify(productRepository, Mockito.times(1)).findBySupplierAndNameAndExpGreaterThanAndStockGreaterThan(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.anyInt(), Mockito.any());
    }

    @Test
    void testShouldBeAbleToRetrieveProductsForGivenSupplierAndProductNameAndExpiredTrue() {

        // Given
        Pageable pageable = PageRequest.of(0, 1);
        ProductEntity product = createProduct();
        Mockito.when(productRepository.findBySupplierAndNameAndStockGreaterThan(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.any()))
                .thenReturn(List.of(product));
        // When
        List<Product> productList = productService.getProductsWithSupplierAndNameAndExpired(SUPPLIER, PRODUCT_NAME, Boolean.TRUE, pageable);

        // Then
        Assertions.assertNotNull(productList);
        Assertions.assertEquals(1, productList.size());
        Assertions.assertEquals(SUPPLIER, productList.get(0).getSupplier());
        Mockito.verify(productRepository, Mockito.times(1)).findBySupplierAndNameAndStockGreaterThan(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.any());
    }

    @Test
    void testShouldBeAbleToSaveCsvFile() {
        // Given
        MockMultipartFile csvFile = new MockMultipartFile("data", "filename.csv", "text/plain", "some csv".getBytes());
        Mockito.when(productRepository.saveAll(Mockito.any())).thenReturn(List.of(createProduct()));
        // When
        productService.saveCsvFile(csvFile);
        // Then
        Mockito.verify(productRepository, Mockito.times(1)).saveAll(Mockito.any());
    }

    @Test
    void testShouldBeAbleThrowExceptionToSaveCsvFile() throws IOException {
        // Given

        // When & Then
        Assertions.assertThrows(IOException.class, () -> productService.saveCsvFile(new MockMultipartFile("file", new FileInputStream(""))));
        Mockito.verify(productRepository, Mockito.never()).saveAll(Mockito.any());
    }

    private ProductEntity createProduct() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setSupplier(SUPPLIER);
        productEntity.setName(PRODUCT_NAME);
        return productEntity;
    }
}
