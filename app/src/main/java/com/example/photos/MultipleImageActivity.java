package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.dogimagefetchinglibrary.view.RecyclerViewAdapter;

import java.util.ArrayList;

public class MultipleImageActivity extends AppCompatActivity {

    private static final String TAG = "MultipleImageActivity";

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<String> listOfImages;
    private ArrayAdapter<String> arrayAdapter;

    private Integer countOfImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_image);
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        listOfImages =  bundle.getStringArrayList("Url_Of_Images");

    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerViewAdapter = new RecyclerViewAdapter(MultipleImageActivity.this, listOfImages);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

}