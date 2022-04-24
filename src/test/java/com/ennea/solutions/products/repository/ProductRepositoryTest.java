package com.ennea.solutions.products.repository;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import com.ennea.solutions.products.entity.ProductEntity;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({DBUnitExtension.class, SpringExtension.class})
@SpringBootTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    private static final String SUPPLIER = "ABBOTT INDIA LIMITED   HYDERABAD";
    private static final String PRODUCT_NAME = "CREMALAX 10MG TAB";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProductRepository repository;

    public ConnectionHolder getConnectionHolder() {
        // Return a function that retrieves a connection from our data source
        return () -> dataSource.getConnection();
    }

    @Test
    @DataSet("products.yml")
    void testFindAll() {
        // When
        List<ProductEntity> products = repository.findAll();
        // Then
        Assertions.assertEquals(2, products.size(), "We should have 2 products in our database");
    }

    @Test
    @DataSet("products.yml")
    void testShouldBeAbleToRetrieveProductsForGivenSupplierWithOneProduct() {
        // Given
        Pageable pageable = PageRequest.of(0, 1);
        // When
        List<ProductEntity> productEntityList = repository.findBySupplierAndStockGreaterThan(SUPPLIER, 0, pageable);

        // Then
        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(1, productEntityList.size());
        Assertions.assertEquals(SUPPLIER, productEntityList.get(0).getSupplier());
    }

    @Test
    @DataSet("products.yml")
    void testShouldBeAbleToRetrieveProductsForGivenSupplier() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        // When
        List<ProductEntity> productEntityList = repository.findBySupplierAndStockGreaterThan(SUPPLIER, 0, pageable);

        // Then
        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(2, productEntityList.size());
        Assertions.assertEquals(SUPPLIER, productEntityList.get(0).getSupplier());
        Assertions.assertEquals("SLDUP", productEntityList.get(1).getCode());
    }

    @Test
    @DataSet("products.yml")
    void testShouldBeAbleToRetrieveNoProductsForUnknownSupplier() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        // When
        List<ProductEntity> productEntityList = repository.findBySupplierAndStockGreaterThan("some supplier", 0, pageable);

        // Then
        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(0, productEntityList.size());
    }

    @Test
    @DataSet("products.yml")
    void testShouldBeAbleToRetrieveProductsForGivenSupplierAndNameWithOneProduct() {
        // Given
        Pageable pageable = PageRequest.of(0, 1);
        // When
        List<ProductEntity> productEntityList = repository.findBySupplierAndNameAndStockGreaterThan(SUPPLIER, PRODUCT_NAME, 0, pageable);

        // Then
        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(1, productEntityList.size());
        Assertions.assertEquals(SUPPLIER, productEntityList.get(0).getSupplier());
        Assertions.assertEquals(PRODUCT_NAME, productEntityList.get(0).getName());
    }

    @Test
    @DataSet("products.yml")
    void testShouldBeAbleToRetrieveProductsForGivenSupplierAndName() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        // When
        List<ProductEntity> productEntityList = repository.findBySupplierAndNameAndStockGreaterThan(SUPPLIER, PRODUCT_NAME, 0, pageable);

        // Then
        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(1, productEntityList.size());
        Assertions.assertEquals(SUPPLIER, productEntityList.get(0).getSupplier());
        Assertions.assertEquals(PRODUCT_NAME, productEntityList.get(0).getName());
    }

    @Test
    @DataSet("products.yml")
    void testShouldBeAbleToRetrieveNoProductsForUnknownSupplierAndName() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        // When
        List<ProductEntity> productEntityList = repository.findBySupplierAndNameAndStockGreaterThan("some supplier", PRODUCT_NAME, 0, pageable);

        // Then
        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(0, productEntityList.size());
    }

    @Test
    @DataSet("products.yml")
    void testShouldBeAbleToRetrieveProductsForGivenSupplierAndExpFlagWithOneProduct() {
        // Given
        Pageable pageable = PageRequest.of(0, 1);
        // When
        List<ProductEntity> productEntityList = repository.findBySupplierAndExpGreaterThanAndStockGreaterThan(SUPPLIER, LocalDate.now(), 0, pageable);

        // Then
        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(1, productEntityList.size());
        Assertions.assertEquals(SUPPLIER, productEntityList.get(0).getSupplier());
        Assertions.assertEquals("DUPHASTON 10MG TAB", productEntityList.get(0).getName());
    }

    @Test
    @DataSet("products.yml")
    void testShouldBeAbleToRetrieveProductsForGivenSupplierAndExpDate() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        // When
        List<ProductEntity> productEntityList = repository.findBySupplierAndExpGreaterThanAndStockGreaterThan(SUPPLIER, LocalDate.now(), 0, pageable);

        // Then
        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(1, productEntityList.size());
        Assertions.assertEquals(SUPPLIER, productEntityList.get(0).getSupplier());
        Assertions.assertEquals("DUPHASTON 10MG TAB", productEntityList.get(0).getName());
    }

    @Test
    @DataSet("products.yml")
    void testShouldBeAbleToRetrieveNoProductsForUnknownSupplierAndExpDate() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        // When
        List<ProductEntity> productEntityList = repository.findBySupplierAndExpGreaterThanAndStockGreaterThan("some supplier", LocalDate.now(), 0, pageable);

        // Then
        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(0, productEntityList.size());
    }

    @Test
    @DataSet("products.yml")
    void testShouldBeAbleToRetrieveProductsForGivenSupplierAndNameAndExpFlagWithOneProduct() {
        // Given
        Pageable pageable = PageRequest.of(0, 1);
        // When
        List<ProductEntity> productEntityList = repository.findBySupplierAndNameAndExpGreaterThanAndStockGreaterThan(SUPPLIER, PRODUCT_NAME, LocalDate.now(), 0, pageable);

        // Then
        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(0, productEntityList.size());
    }

    @Test
    @DataSet("products.yml")
    void testShouldBeAbleToRetrieveProductsForGivenSupplierAndNameAndExpDate() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        // When
        List<ProductEntity> productEntityList = repository.findBySupplierAndNameAndExpGreaterThanAndStockGreaterThan(SUPPLIER, PRODUCT_NAME, LocalDate.now(), 0, pageable);

        // Then
        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(0, productEntityList.size());
    }

    @Test
    @DataSet("products.yml")
    void testShouldBeAbleToRetrieveNoProductsForUnknownSupplierAndNameAndExpDate() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        // When
        List<ProductEntity> productEntityList = repository.findBySupplierAndNameAndExpGreaterThanAndStockGreaterThan("some supplier", PRODUCT_NAME, LocalDate.now(), 0, pageable);

        // Then
        Assertions.assertNotNull(productEntityList);
        Assertions.assertEquals(0, productEntityList.size());
    }
}
