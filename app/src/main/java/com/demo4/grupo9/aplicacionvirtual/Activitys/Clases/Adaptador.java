package com.demo4.grupo9.aplicacionvirtual.Activitys.Clases;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.demo4.grupo9.aplicacionvirtual.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    private List<Movie> movies;
    private int layout;
    private OnitemClickListener itemClickListener;
    private Context context;

    public Adaptador(List<Movie> movies, int layout, OnitemClickListener listener){
        this.movies = movies;
        this.layout = layout;
        this.itemClickListener = listener;
    }



    @Override
    public Adaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent,false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(movies.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    // aqui es donde se renderiza los atributos

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public ImageView imageViewPhoto;

        public ViewHolder(View itemView){
            super(itemView);

            this.textName = itemView.findViewById(R.id.textViewtitle);
            this.imageViewPhoto = itemView.findViewById(R.id.imageViewPhoto);

        }

        public void bind(final Movie movie, final OnitemClickListener listener){

            textName.setText(movie.getName());
            Picasso.with(context).load(movie.getPhoto()).fit().into(imageViewPhoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(movie,getAdapterPosition());
                }
            });
        }
    }
    public interface OnitemClickListener {
        void onItemClick(Movie movie, int position);
    }
}
