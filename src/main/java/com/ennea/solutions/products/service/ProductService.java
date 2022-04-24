package com.ennea.solutions.products.service;

import java.util.List;

import com.ennea.solutions.products.model.Product;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    /**
     * Take the csv file and uploads in DB
     *
     * @param file      csv file
     */
    void saveCsvFile(MultipartFile file);

    /**
     * Returns the products with the specified supplier.
     *
     * @param supplier  Name of the supplier
     * @param pageable  JPA pageable object
     * @return          List of products
     */
    List<Product> getProductsWithSupplier(String supplier, Pageable pageable);

    /**
     * Returns the products with the specified supplier and name.
     *
     * @param supplier  Name of the supplier
     * @param name      Name of the product
     * @param pageable  JPA pageable object
     * @return          List of products
     */
    List<Product> getProductsWithSupplierAndName(String supplier, String name, Pageable pageable);

    /**
     * Returns the products with the specified supplier and name.
     *
     * @param supplier  Name of the supplier
     * @param expired   Boolean flag to retrieve expired products
     * @param pageable  JPA pageable object
     * @return          List of products
     */
    List<Product> getProductsWithSupplierAndExpired(String supplier, Boolean expired, Pageable pageable);

    /**
     * Returns the products with the specified supplier and name.
     *
     * @param supplier  Name of the supplier
     * @param name      Name of the product
     * @param expired   Boolean flag to retrieve expired products
     * @param pageable  JPA pageable object
     * @return          List of products
     */
    List<Product> getProductsWithSupplierAndNameAndExpired(String supplier, String name, Boolean expired, Pageable pageable);
}
