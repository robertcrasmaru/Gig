package com.example.crasm.gig.Common;

import com.example.crasm.gig.Remote.IGoogleAPI;
import com.example.crasm.gig.Remote.RetrofitClient;

/**
 * Created by crasm on 16.05.2018.
 */

public class Common {

    public static final String baseURL= "https://maps.googleapis.com";
    public static IGoogleAPI getGoogleAPI()
    {
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }
}
