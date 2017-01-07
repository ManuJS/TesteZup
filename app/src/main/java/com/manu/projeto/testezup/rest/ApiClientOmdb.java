package com.manu.projeto.testezup.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//serve tanto pra lista geral quanto pra detalhes

public class ApiClientOmdb {

    public static final String BASE_URL = "http://www.omdbapi.com/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
