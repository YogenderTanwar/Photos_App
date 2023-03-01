package com.example.dogimagefetchinglibrary.service.repository;

import com.example.dogimagefetchinglibrary.service.model.Image;
import com.example.dogimagefetchinglibrary.service.util.ImageFetchingService;
import com.example.dogimagefetchinglibrary.service.util.RetrofitClientInstance;

import java.util.List;

import io.reactivex.rxjava3.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ImageRepository {
    private static final String TAG = "ImageRepository";
    final private ImageFetchingService imageFetchingService;
    private  static  ImageRepository imageRepository;
    public PublishSubject<String> singleImageViewModelToModelObservable;
    public PublishSubject<String> singleImageViewModelToModelObservableError;
    public PublishSubject<List<String>> multiImageViewModelToModelObservable;
    public PublishSubject<String> multiImageViewModelToModelObservableError;


    private ImageRepository() {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        imageFetchingService = RetrofitClientInstance.getRetrofitInstance().create(ImageFetchingService.class);
        singleImageViewModelToModelObservable = PublishSubject.create();
        singleImageViewModelToModelObservableError = PublishSubject.create();
        multiImageViewModelToModelObservable = PublishSubject.create();
        multiImageViewModelToModelObservableError = PublishSubject.create();
    }

    public  synchronized  static  ImageRepository getInstance() {
        if(imageRepository == null) {
            imageRepository = new ImageRepository();
        }
        return imageRepository;
    }

    public void getImage() {
        Call<Image> urlRequest = imageFetchingService.getPhoto();
        urlRequest.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                String url = response.body().getMessage().get(0);
                singleImageViewModelToModelObservable.onNext(url);
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                singleImageViewModelToModelObservableError.onNext("Please check your internet connection");
            }
        });
    }

    public void getImages(int countOfImages) {
        Call<Image> call = imageFetchingService.getImages(countOfImages);
        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                multiImageViewModelToModelObservable.onNext(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                multiImageViewModelToModelObservableError.onNext("Please check your internet connection");
            }
        });
    }
}
