package com.example.photos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import com.example.dogimagefetchinglibrary.viewmodel.ImageViewModel;




public final class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageView imageView;
    private Button nextImageButton;
    private EditText editText;
    private Button submitButton;
    private Button previousImageButton;
    private ProgressBar progressBar;
    private ImageViewModel imageViewModel;

    private  String currentImageUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        nextImageButton = findViewById(R.id.nextimagebutton);
        previousImageButton = findViewById(R.id.previousimagebutton);
        editText = findViewById(R.id.edittext);
        submitButton = findViewById(R.id.submitbutton);
        progressBar = findViewById(R.id.loadingPanel);
        imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        listenToObservable();
        if(savedInstanceState == null) {
            imageViewModel.getImage();
        }
        else {
            currentImageUrl = savedInstanceState.getString("url");
            Glide.with(getApplicationContext()).load(currentImageUrl).into(imageView);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        nextImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imageViewModel.getNextImage();
            }
        });
        previousImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewModel.getPreviousImage();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if (text.isEmpty()) return;
                Integer currValue = Integer.valueOf(text);
                if (inputTextValidation(currValue)) {
                    imageViewModel.getImages(currValue);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter value between 1 to 10", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void listenToObservable() {
        imageViewModel.singleImageActivityToViewModelObservable.subscribe(
                url -> {
                    currentImageUrl = url;
                    Glide.with(getApplicationContext()).load(url).into(imageView);
                },
                throwable -> Log.d(TAG, "Throwable " + throwable.getMessage())
        );


        imageViewModel.singleImageActivityToViewModelObservableError.subscribe(
                value -> Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show(),
                throwable -> Log.d(TAG, "Throwable " + throwable.getMessage())
        );

        imageViewModel.multiImageActivityToViewModelObservable.subscribe(
                urlOfImages -> {
                    Intent myIntent = new Intent(MainActivity.this, MultipleImageActivity.class);
                    myIntent.putExtra("Url_Of_Images", new ArrayList<String>(urlOfImages)); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
                },
                throwable -> Log.d(TAG, "Throwable " + throwable.getMessage())
        );

        imageViewModel.internetConnectionErrorImageObservable.subscribe(
                value -> {
                    imageView.setImageResource(R.drawable.no_connection);
                },
                throwable -> Log.d(TAG, "Throwable " + throwable.getMessage())
        );
    }
    private Boolean inputTextValidation(Integer value) {
        return value > 0 && value <= 10;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("url",currentImageUrl);
    }

}