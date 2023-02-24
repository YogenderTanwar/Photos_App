package com.example.photos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.photos.Retrofit.GetDataService;
import com.example.photos.Retrofit.RetrofitClientInstance;
import com.example.photos.model.Image;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public final class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageView imageView;
    private Button nextImageButton;
    private EditText editText;
    private  Button submitButton;
    private Button previousImageButton;
    private  GetDataService service;

    private ArrayList arrayList;

    int currIndex = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        nextImageButton = findViewById(R.id.nextimagebutton);
        previousImageButton = findViewById(R.id.previousimagebutton);
        editText = findViewById(R.id.edittext);
        submitButton = findViewById(R.id.submitbutton);



        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        arrayList = new ArrayList<String> ();



    }

    @Override
    protected void onResume() {
        super.onResume();

        showImage();

        nextImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(currIndex == arrayList.size()-1) {
                    showImage();
                }
                else {
                    currIndex++;
                    Log.d(TAG, "Next Image " + currIndex );
                    Glide.with(MainActivity.this).load(arrayList.get(currIndex)).into(imageView);
                }

            }
        });

        previousImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currIndex<=0) {
                    Toast.makeText(MainActivity.this, "No Previous Image Available Right now", Toast.LENGTH_SHORT).show();
                }
                else {
                   currIndex--;
                    Log.d(TAG, "Previous Image " + currIndex );
                    Glide.with(MainActivity.this).load(arrayList.get(currIndex)).into(imageView);

                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();


                if(text.isEmpty()) return ;
                Integer currValue = Integer.valueOf(text);

                if(currValue>0 && currValue <= 10 ) {
                    Toast.makeText(MainActivity.this, "Horray correct value", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(MainActivity.this, MultipleImageActivity.class);
                    myIntent.putExtra("Total_Images", currValue); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
                }
                else {

                    Toast.makeText(MainActivity.this, "Please enter value between 1 to 10", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void showImage() {
        Call<Image> call = service.getPhoto();
        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
//                System.out.println(response.body());
                Log.d(TAG,response.body().toString());

                arrayList.add(response.body().getMessage().get(0));
                currIndex++;
                Glide.with(MainActivity.this).load(response.body().getMessage().get(0)).into(imageView);
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}