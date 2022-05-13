package com.shekoo.popflake.ui.search

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
import com.shekoo.popflake.MainActivity
import com.shekoo.popflake.R
import com.shekoo.popflake.model.entities.Results
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

class SearchAdapter (private val activity: MainActivity): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private val items: MutableList<Results> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.searchTitleTextView.text = items[position].title
        holder.searchDescriptionTextView.text = items[position].description

        Glide.with(holder.searchImageView.context).load(items[position].image).fitCenter()
            .into(holder.searchImageView)

        holder.searchConstrainLayout.setOnClickListener {
            val url = items[position].id
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/$url"))
            activity.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addList(listOfItems: List<Results>) {
        items.clear()
        items.addAll(listOfItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val searchDescriptionTextView: TextView
            get() = itemView.findViewById(R.id.searchDescriptionTextView)

        val searchTitleTextView: TextView
            get() = itemView.findViewById(R.id.searchTitleTextView)

        val searchImageView: ImageView
            get() = itemView.findViewById(R.id.searchImageView)

        val searchConstrainLayout: ConstraintLayout
            get() = itemView.findViewById(R.id.searchConstrainLayout)
    }
}