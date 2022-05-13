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

class AdapterComingSoon(var startUrlIntent: (Intent) -> Unit) :
    RecyclerView.Adapter<AdapterComingSoon.ViewHolder>() {

    private val items: MutableList<Items> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTextView.text = items[position].title
        holder.comingSoonReleaseTextView.text = items[position].releaseState
        holder.comingSoonDurationTextView.text = items[position].runtimeStr
        holder.rate.visibility = View.GONE
        Glide.with(holder.movieImageView.context).load(items[position].image).centerCrop()
            .into(holder.movieImageView)
        holder.topMovieConstrainLayout.setOnClickListener {
            //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/movies-coming-soon"))
            val url = items[position].id
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/$url"))
            startUrlIntent(intent)
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

        val comingSoonReleaseTextView: TextView
            get() = itemView.findViewById(R.id.comingSoonReleaseTextView)

        val comingSoonDurationTextView: TextView
            get() = itemView.findViewById(R.id.comingSoonDurationTextView)

        val rate: ImageView
            get() = itemView.findViewById(R.id.rate)

        val movieImageView: ImageView
            get() = itemView.findViewById(R.id.movieImageView)

        val topMovieConstrainLayout: ConstraintLayout
            get() = itemView.findViewById(R.id.topMovieConstrainLayout)
    }
}