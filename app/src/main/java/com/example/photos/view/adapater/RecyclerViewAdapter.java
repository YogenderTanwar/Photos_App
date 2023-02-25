package com.example.photos.view.adapater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photos.MainActivity;
import com.example.photos.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    final private Context context;
    final private List<String> listOfImages;

    public RecyclerViewAdapter(Context context, List<String> listOfImages) {
        this.context = context;
        this.listOfImages = listOfImages;
    }

    // Where to get the single card as viewholder Object
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    // What will happen after we create the viewholder object
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
       String url = listOfImages.get(position);
        Glide.with(context).load(url).into(holder.imageView);
    }

    // How many items?
    @Override
    public int getItemCount() {
        return listOfImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.imageview);
        }

        @Override
        public void onClick(View view) {
            Log.d("ClickFromViewHolder", "Clicked");
        }
    }
}

