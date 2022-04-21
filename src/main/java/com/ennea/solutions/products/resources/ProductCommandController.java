package com.ennea.solutions.products.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCommandController {

    @RequestMapping
    public String helloWorld() {
        return "Hello";
    }


}
