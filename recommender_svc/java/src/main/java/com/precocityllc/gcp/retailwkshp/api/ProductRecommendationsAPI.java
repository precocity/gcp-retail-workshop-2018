package com.precocityllc.gcp.retailwkshp.api;

import com.precocityllc.gcp.retailwkshp.model.IRecsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ProductRecommendationsAPI {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ProductRecommendationsAPI.class);

    private IRecsModel recommendationModel;

    public ProductRecommendationsAPI(IRecsModel recModel) {
        try {
            recModel.loadModel();
            this.recommendationModel = recModel;
        }
        catch(Exception ex) {
            LOGGER.error("Error loading model data: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error loading model data");
        }
    }


    @GetMapping("/customer/{customer_id}/recs")
    public List<String> getRecommendationsForUser(@PathVariable("customer_id") String customerId,
                                                  @RequestParam(value = "numRecs", defaultValue = "5") int numberOfRecs) {
        return recommendationModel.generateRecommendations(customerId, numberOfRecs);
    }
}