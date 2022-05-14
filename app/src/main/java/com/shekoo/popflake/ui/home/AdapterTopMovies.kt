package com.shekoo.popflake.ui.home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shekoo.popflake.R
import com.shekoo.popflake.model.entities.Items

class AdapterTopMovies : RecyclerView.Adapter<AdapterTopMovies.ViewHolder>() {

    private val items: MutableList<Items> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTextView.text = items[position].title
        holder.topMovieYearTextView.text = items[position].year.toString()
        holder.topMovieRateTextView.text = items[position].imDbRating.toString()
        Glide.with(holder.topMovieImageView.context).load(items[position].image).centerCrop()
            .into(holder.topMovieImageView)

        holder.topMovieConstrainLayout.setOnClickListener {
            //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/chart/top"))
            val url = items[position].id
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/$url"))
            holder.topMovieImageView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addList(listOfItems: List<Items>) {
        items.addAll(listOfItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val movieTextView: TextView
            get() = itemView.findViewById(R.id.movieTextView)

        val topMovieYearTextView: TextView
            get() = itemView.findViewById(R.id.topMovieyearTextView)

        val topMovieRateTextView: TextView
            get() = itemView.findViewById(R.id.topMovieRateTextView)

        val topMovieImageView: ImageView
            get() = itemView.findViewById(R.id.movieImageView)

        val topMovieConstrainLayout: ConstraintLayout
            get() = itemView.findViewById(R.id.topMovieConstrainLayout)
    }
}