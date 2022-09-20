package com.example.flixsterp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
//RecyclerAdapter which takes in List of Movie class
class RecyclerAdapter(private val movies: ArrayList<Movie>) : RecyclerView.Adapter<RecyclerAdapter.MovieHolder>() {
   // Creates references to the views in row_item.xml
    class MovieHolder(v: View) : RecyclerView.ViewHolder(v) {
        var ItemImage: ImageView
        var ItemTitle: TextView
        var ItemDescript: TextView

        init {
            ItemImage = v.findViewById(R.id.backdrop)
            ItemTitle = v.findViewById(R.id.title)
            ItemDescript = v.findViewById(R.id.description)
        }
    }

    // creates a row_item.xml for each row
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.MovieHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MovieHolder(v)
    }

    // When view is created. Load the image using picasso and set the title and descript
    override fun onBindViewHolder(holder: RecyclerAdapter.MovieHolder, position: Int) {
        val movie = movies[position]
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movie.backdrop_path).into(holder.ItemImage)
        holder.ItemDescript.setText(movie.overview)
        holder.ItemTitle.setText(movie.original_title)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}