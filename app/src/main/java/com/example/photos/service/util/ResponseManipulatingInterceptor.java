package com.example.photos.service.util;

import android.util.Log;


import com.example.photos.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseManipulatingInterceptor implements Interceptor {

    private static final String TAG = "ResponseManipulatingInterceptor";

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String rawJson = response.body().string();
        try {
            JSONObject json = new JSONObject(rawJson);
            Object object = json.get("message");
            if(object.getClass().equals(String.class))
            {
                String url = object.toString();
                json.remove("message");
                ArrayList<String> list = new ArrayList<String>();
                list.add(url);
                json.put("message", new JSONArray(list));
                rawJson = json.toString();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), rawJson)).build();
    }
}