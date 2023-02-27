package com.example.photos.viewmodel;




import android.util.Log;
import androidx.lifecycle.ViewModel;

import com.example.photos.service.repository.ImageRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class ImageViewModel extends ViewModel {
    private static final String TAG = "ImageViewModel";
    List<String> urlOfImages;
    Integer currImageIndex;
    ImageRepository imageRepository;
    public PublishSubject<String> singleImageActivityToViewModelObservable;
    public PublishSubject<String> singleImageActivityToViewModelObservableError;
    public  PublishSubject<String> internetConnectionErrorImageObservable;
    public PublishSubject<List<String>> multiImageActivityToViewModelObservable ;


    public ImageViewModel() {
        this.urlOfImages = new ArrayList<>();
        this.currImageIndex = -1;
        imageRepository = ImageRepository.getInstance();
        singleImageActivityToViewModelObservable = PublishSubject.create();
        singleImageActivityToViewModelObservableError = PublishSubject.create();
        multiImageActivityToViewModelObservable = PublishSubject.create();
        internetConnectionErrorImageObservable = PublishSubject.create();
        listenToObservable();
    }
    public void getImage() {
        imageRepository.getImage();
    }

    public void getNextImage() {
        if (currImageIndex == (urlOfImages.size() - 1)) {
            getImage();
        } else {
            currImageIndex++;
            sendImageUrlToView(currImageIndex);
        }
    }

    public void getPreviousImage() {
        if (currImageIndex > 0) {
            currImageIndex--;
            sendImageUrlToView(currImageIndex);
        } else {
            singleImageActivityToViewModelObservableError.onNext("No Previous Image Available.");
        }
    }

    public void getImages(int countOfImages) {
        imageRepository.getImages(countOfImages);
    }

    private void sendImageUrlToView(Integer currIndex) {
        singleImageActivityToViewModelObservable.onNext(urlOfImages.get(currImageIndex));
    }

    private void listenToObservable() {
        imageRepository.singleImageViewModelToModelObservable.subscribe(value -> {
                    currImageIndex++;
                    urlOfImages.add(value);
                    sendImageUrlToView(currImageIndex);
                },
                throwable -> Log.d(TAG, "Throwable " + throwable.getMessage())
        );

        imageRepository.singleImageViewModelToModelObservableError.subscribe(
                value -> {
                    if(this.currImageIndex == -1) {
                        this.internetConnectionErrorImageObservable.onNext("No internet connection");
                    }
                    singleImageActivityToViewModelObservableError.onNext(value);
                },
                throwable -> Log.d(TAG, "Throwable " + throwable.getMessage())
        );

        imageRepository.multiImageViewModelToModelObservable.subscribe( listOfUrl -> {
                    multiImageActivityToViewModelObservable.onNext(listOfUrl);
                },
                throwable -> Log.d(TAG, "Throwable " + throwable.getMessage())
        );

        imageRepository.multiImageViewModelToModelObservableError.subscribe(
                value -> singleImageActivityToViewModelObservableError.onNext(value),
                throwable -> Log.d(TAG, "Throwable " + throwable.getMessage())
        );
    }
}
