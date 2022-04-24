package com.ennea.solutions.products.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class Product {
    private String code;
    private String name;
    private String batch;
    private Integer stock;
    private Integer deal;
    private Integer free;
    private BigDecimal mrp;
    private BigDecimal rate;
    private LocalDate exp;
    private String company;
    private String supplier;
}
