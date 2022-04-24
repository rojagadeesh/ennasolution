package com.ennea.solutions.products.resources;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ennea.solutions.products.helper.CsvHelper;
import com.ennea.solutions.products.model.Product;
import com.ennea.solutions.products.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class ProductCommandController {

    @Autowired
    private final ProductService productService;

    @Operation(hidden = true)
    @GetMapping("/initialcheck")
    public String helloWorld() {
        return "Hello";
    }

    /**
     * Upload csv file
     * @param file   csvfile.
     * @return String.
     */
    @Operation(summary = "Upload products csv", tags = "Post")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Products saved successfully", content = {@Content(mediaType = "application/txt", schema = @Schema(implementation = String.class))})})
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String uploadData(@RequestParam("file") MultipartFile file) throws IOException {
        if (CsvHelper.hasCSVFormat(file)) {
            try {
                productService.saveCsvFile(file);
                return "Uploaded the file successfully: " + file.getOriginalFilename();
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not upload the file: " + file.getOriginalFilename(), e);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please upload a csv file!");
    }

    /**
     * Retrieves list of products with specified Supplier
     * @param supplier - Name of supplier
     * @param name - Name of the Products (optional)
     * @param expired - If false, then list products didnot expire
     * @return Products
     */
    @Operation(summary = "Retrieve Products", tags = "Get")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Products List", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))})})
    @GetMapping("/products/{supplier}")
    public List<Product> getProductsWithSupplier(
            @PathVariable String supplier,
            @RequestParam Optional<String> name,
            @RequestParam Optional<Boolean> expired,
            Pageable pageable) {
        if (name.isPresent() && expired.isPresent()) {
            return productService.getProductsWithSupplierAndNameAndExpired(supplier, name.get(), expired.get(), pageable);
        } else if (name.isPresent()) {
            return productService.getProductsWithSupplierAndName(supplier, name.get(), pageable);
        } else if (expired.isPresent()) {
            return productService.getProductsWithSupplierAndExpired(supplier, expired.get(), pageable);
        } else {
            return productService.getProductsWithSupplier(supplier, pageable);
        }
    }
}
