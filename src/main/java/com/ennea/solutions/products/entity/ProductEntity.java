package com.ennea.solutions.products.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvIgnore;
import com.opencsv.bean.CsvNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvIgnore
    private Long id;

    @CsvBindByName(column = "code")
    private String code;

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "batch")
    private String batch;

    @CsvBindByName(column = "stock")
    private Integer stock;

    @CsvBindByName(column = "deal")
    private Integer deal;

    @CsvBindByName(column = "free")
    private Integer free;

    @CsvBindByName(column = "mrp")
    @CsvNumber("########.##")
    private BigDecimal mrp;

    @CsvBindByName(column = "rate")
    @CsvNumber("########.##")
    private BigDecimal rate;

    @CsvBindByName(column = "exp")
    @CsvDate("dd/MM/yyyy")
    private LocalDate exp;

    @CsvBindByName(column = "company")
    private String company;

    @CsvBindByName(column = "supplier")
    private String supplier;
}
