package com.example.photos.Retrofit;

import com.example.photos.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {

    @GET("random")
    public Call<Image> getPhoto();

    @GET("random/{countOfImages}")
    public Call<Image> getImages(@Path(value="countOfImages") Integer countOfImages);
}
