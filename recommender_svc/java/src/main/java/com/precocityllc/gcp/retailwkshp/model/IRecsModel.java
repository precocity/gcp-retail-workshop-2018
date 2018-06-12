package com.precocityllc.gcp.retailwkshp.model;

import java.util.List;

public interface IRecsModel {

    public void loadModel() throws Exception;
    public List<String> generateRecommendations(String userId, int numRecommendations);
}
