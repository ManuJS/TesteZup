package com.manu.projeto.testezup.rest;

import com.manu.projeto.testezup.model.OmdbMoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterfaceOmdb {
    @GET("/")
    Call<OmdbMoviesResponse> search(@Query("s") String q, @Query("r") String format);


}
