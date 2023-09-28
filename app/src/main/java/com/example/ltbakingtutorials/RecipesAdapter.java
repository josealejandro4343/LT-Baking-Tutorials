package com.example.ltbakingtutorials;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class RecipesAdapter extends FirebaseRecyclerAdapter<Recipes, RecipesAdapter.myViewHolder> {
    private Context context;
    private ArrayList<ImageModel> imageModelArrayList;
    ArrayList<Recipes> list;

    public RecipesAdapter(@NonNull FirebaseRecyclerOptions<Recipes> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Recipes model) {
        holder.RecipeName.setText(model.getRecipeName());
        Glide.with(holder.img.getContext()).load(model.getRecipeImage()).into(holder.img);
        holder.ratingBar.setRating(model.getDifficulty());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_image_layout, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView RecipeName;
        RatingBar ratingBar;


        public myViewHolder(@NonNull View itemView){
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.image_view);
            RecipeName = (TextView) itemView.findViewById(R.id.recipeName1);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

        }
    }
}
