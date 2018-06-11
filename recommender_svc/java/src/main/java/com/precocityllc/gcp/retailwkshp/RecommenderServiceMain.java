package com.precocityllc.gcp.retailwkshp;

import com.precocityllc.gcp.retailwkshp.model.RecsModel;
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

    public static void loadModelData() {
        RecsModel modelLoader = new RecsModel("precocity-retail-workshop-2018",
                    "recommender_precocity-retail-workshop-2018",
                "model/row.csv", "model/col.csv", "model/user.csv",
                "model/item.csv", "data/recommendation_events.csv");
    }

    public static void main(String []  args) {

        loadModelData();
        SpringApplication.run(RecommenderServiceMain.class, args);
    }
}