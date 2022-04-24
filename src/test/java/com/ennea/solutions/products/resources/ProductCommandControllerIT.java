package com.ennea.solutions.products.resources;

import javax.sql.DataSource;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({DBUnitExtension.class, SpringExtension.class})
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductCommandControllerIT {

    private static final String SUPPLIER = "ABBOTT INDIA LIMITED   HYDERABAD";
    private static final String PRODUCT_NAME = "CREMALAX 10MG TAB";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    public ConnectionHolder getConnectionHolder() {
        // Return a function that retrieves a connection from our data source
        return () -> dataSource.getConnection();
    }

    @Test
    @DisplayName("GET /products/Supplier - Found")
    @DataSet("products.yml")
    void testGetProductBySupplierFound() throws Exception {
        // Execute the GET request
        mockMvc.perform(get("/products/{supplier}", SUPPLIER))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate the returned fields
                .andExpect(jsonPath("$.[0].name", is(PRODUCT_NAME)))
                .andExpect(jsonPath("$.[0].supplier", is(SUPPLIER)));
    }
}
