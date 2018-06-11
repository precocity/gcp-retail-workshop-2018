package com.precocityllc.gcp.retailwkshp.api;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ProductRecommendationsAPI {

    @GetMapping("/customer/{customer_id}/recs")
    public String getRecommendationsForUser(@PathVariable("customer_id") String customerId) {
        return "123, 456, 789";
    }
}