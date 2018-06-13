package com.precocityllc.gcp.retailwkshp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class RecommenderServiceMain {

    protected static final Logger LOGGER  = LoggerFactory.getLogger(RecommenderServiceMain.class);

    public static void main(String []  args) {
        SpringApplication.run(RecommenderServiceMain.class, args);
    }
}