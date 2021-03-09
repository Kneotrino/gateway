package com.github.kneotrino.gateway.common.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.URL;
import java.net.URLConnection;

public class ConnectionUtil {
    public static boolean isConnectedToServer(String url, int timeout) {
        try {
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (Exception e) {
            // todo Handle exceptions
            return false;
        }
    }

    public static Retrofit BuildClient(String URL) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.GetDefaultGson()))
                .build();

    }
}
