package com.shekoo.popflake.ui.home

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shekoo.popflake.R
import com.shekoo.popflake.model.entities.TopMovies

class TopMoviesAdapter(var listOfTopMovies : TopMovies) : RecyclerView.Adapter<TopMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.top_movies_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.topMovieTextView.text= listOfTopMovies.items[position].title
        holder.topMovieYearTextView.text= listOfTopMovies.items[position].year.toString()
        Glide.with(holder.topMovieImageView.context).load(listOfTopMovies.items[position].image).centerCrop().into(holder.topMovieImageView)
        holder.topMovieConstrainLayout.setOnClickListener {

            Toast.makeText(it.context, "here", Toast.LENGTH_SHORT).show()

        }

    }

    override fun getItemCount(): Int {
        return listOfTopMovies.items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val topMovieTextView : TextView
        get() = itemView.findViewById(R.id.topMovieTextView)

        val topMovieYearTextView : TextView
        get() = itemView.findViewById(R.id.topMovieyearTextView)

        val topMovieImageView : ImageView
        get() = itemView.findViewById(R.id.topMovieImageView)

        val topMovieConstrainLayout : ConstraintLayout
        get() = itemView.findViewById(R.id.topMovieConstrainLayout)
    }
}