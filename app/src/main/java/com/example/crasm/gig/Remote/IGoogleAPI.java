package com.example.crasm.gig.Remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by crasm on 16.05.2018.
 */

public interface IGoogleAPI {
    @GET
    Call<String> getPath(@Url String url);
}