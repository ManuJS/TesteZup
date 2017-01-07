package com.manu.projeto.testezup.rest;

import com.manu.projeto.testezup.model.OmdbMoviesResponseDetail;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by emanu on 06/01/2017.
 */

public interface OmdbServiceDetail {
    @GET("/")
    Call<OmdbMoviesResponseDetail> repoContributors(
            @Query("i") String idMovie,
            @Query("r") String format);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}