# ennasolution

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

Clone
--------

```sh
git clone https://github.com/rojagadeesh/ennasolution.git
```

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.ennea.solutions.products.ProductsApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
Access
--------

### For General usage

```
http://localhost:8080/
```

### Swagger Definition
Enabled in port 9090
```
http://localhost:9090/actuator/swagger-ui/index.html
```

License
-------

Apache License, Version 2.0

[spring-boot]: https://github.com/spring-projects/spring-boot
