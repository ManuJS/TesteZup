package com.manu.projeto.testezup.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class OmdbMoviesResponse {

    @SerializedName("Search")
    private List<OmdbMovie> results;


    public List<OmdbMovie> getResults() {
        return results;
    }

    public void setResults(List<OmdbMovie> results) {
        this.results = results;
    }


}
