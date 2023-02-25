package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.photos.Retrofit.GetDataService;
import com.example.photos.Retrofit.RetrofitClientInstance;
import com.example.photos.model.Image;
import com.example.photos.view.adapater.RecyclerViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultipleImageActivity extends AppCompatActivity {

    private static final String TAG = "MultipleImageActivity";
    private  GetDataService service;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private  List<String> listOfImages;
    private ArrayAdapter<String> arrayAdapter;

    private  Integer countOfImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_image);

        //Recyclerview initialization
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();

        countOfImages =  intent.getIntExtra("Total_Images",1);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        showImages();

    }

    private void showImages() {
        Call<Image> call = service.getImages(countOfImages);
        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                assert response.body() != null;
                listOfImages = response.body().getMessage();
                recyclerViewAdapter = new RecyclerViewAdapter(MultipleImageActivity.this, listOfImages);
                recyclerView.setAdapter(recyclerViewAdapter);

                for( String url : response.body().getMessage() ) {
                    Log.d(TAG, "onResponse of Multiple Images" + url);
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {

                Toast.makeText(MultipleImageActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}