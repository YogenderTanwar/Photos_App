package com.example.photos.service.util;


import com.example.photos.service.model.Image;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImageFetchingService {

    @GET("random")
    public Call<Image> getPhoto();

    @GET("random/{countOfImages}")
    public Call<Image> getImages(@Path(value="countOfImages") Integer countOfImages);
}
