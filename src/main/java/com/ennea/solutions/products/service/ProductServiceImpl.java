package com.ennea.solutions.products.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.ennea.solutions.products.entity.ProductEntity;
import com.ennea.solutions.products.helper.CsvHelper;
import com.ennea.solutions.products.model.Product;
import com.ennea.solutions.products.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public void saveCsvFile(MultipartFile file) {
        try {
            List<ProductEntity> productEntityList = CsvHelper.csvToProducts(file.getInputStream());
            productRepository.saveAll(productEntityList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getProductsWithSupplier(String supplier, Pageable pageable) {
        return mapProductEntityToProducts(productRepository.findBySupplierAndStockGreaterThan(supplier, 0, pageable));
    }

    @Override
    public List<Product> getProductsWithSupplierAndName(String supplier, String name, Pageable pageable) {
        return mapProductEntityToProducts(productRepository.findBySupplierAndNameAndStockGreaterThan(supplier, name, 0, pageable));
    }

    @Override
    public List<Product> getProductsWithSupplierAndExpired(String supplier, Boolean expired, Pageable pageable) {
        List<ProductEntity> productEntityList;
        if (!expired) {
            productEntityList = productRepository.findBySupplierAndExpGreaterThanAndStockGreaterThan(supplier, LocalDate.now(), 0, pageable);
        } else {
            productEntityList = productRepository.findBySupplierAndStockGreaterThan(supplier, 0, pageable);
        }
        return mapProductEntityToProducts(productEntityList);
    }

    @Override
    public List<Product> getProductsWithSupplierAndNameAndExpired(
            String supplier,
            String name,
            Boolean expired,
            Pageable pageable) {
        List<ProductEntity> productEntityList;
        if (!expired) {
            productEntityList = productRepository.findBySupplierAndNameAndExpGreaterThanAndStockGreaterThan(supplier, name, LocalDate.now(), 0, pageable);
        } else {
            productEntityList = productRepository.findBySupplierAndNameAndStockGreaterThan(supplier, name, 0, pageable);
        }
        return mapProductEntityToProducts(productEntityList);
    }

    private List<Product> mapProductEntityToProducts(List<ProductEntity> productEntityList) {
        return productEntityList.stream().map(
                productEntity -> Product.builder()
                        .code(productEntity.getCode())
                        .name(productEntity.getName())
                        .batch(productEntity.getBatch())
                        .stock(productEntity.getStock())
                        .deal(productEntity.getDeal())
                        .free(productEntity.getFree())
                        .mrp(productEntity.getMrp())
                        .rate(productEntity.getRate())
                        .exp(productEntity.getExp())
                        .company(productEntity.getCompany())
                        .supplier(productEntity.getSupplier())
                        .build()).collect(Collectors.toList());
    }
}
