package com.ennea.solutions.products.helper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.ennea.solutions.products.entity.ProductEntity;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.multipart.MultipartFile;

@Slf4j
public final class CsvHelper {
    public static final String TYPE = "text/csv";

    private CsvHelper() {

    }

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<ProductEntity> csvToProducts(InputStream is) {
        final CsvToBean<ProductEntity> beans =
                new CsvToBeanBuilder<ProductEntity>(new InputStreamReader(is))
                        .withType(ProductEntity.class)
                        .withThrowExceptions(false)
                        .build();

        final List<ProductEntity> products = beans.parse();

        beans.getCapturedExceptions().stream().forEach((exception) -> {
            log.error("Inconsistent data:" +
                    String.join("", exception.getLine()), exception);
        });

        return products;
    }
}
