package com.precocityllc.gcp.retailwkshp.model;

import org.junit.Test;

import java.util.List;

public class RecsModelTest {

    @Test
    public void testLoadModel() throws Exception {
        RecsModel model = new RecsModel("precocity-retail-workshop-2018",
                "recommender_precocity-retail-workshop-2018",
                "model/row.csv", "model/col.csv", "model/user.csv",
                "model/item.csv", "data/recommendation_events.csv");
        model.loadModel();

        List<String> recs = model.generateRecommendations("00122544416887869", 5);
        recs.forEach(rec -> System.out.println(rec));
    }

}