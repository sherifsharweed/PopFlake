package com.shekoo.popflake.ui.home

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

class AdapterBoxOffice : RecyclerView.Adapter<AdapterBoxOffice.ViewHolder>() {
    private val items: MutableList<Items> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_box, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.boxOfficeRankTextView.text = items[position].rank
        holder.boxOfficeTitleTextView.text = items[position].title
        holder.boxOfficeWeekEndTextView.text = items[position].weekend

        Glide.with(holder.boxOfficeImageView.context).load(items[position].image).circleCrop()
            .into(holder.boxOfficeImageView)
        holder.boxOfficeConstrainLayout.setOnClickListener {
            Toast.makeText(it.context, "here", Toast.LENGTH_SHORT).show()
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

        val boxOfficeRankTextView: TextView
            get() = itemView.findViewById(R.id.boxOfficeRankTextView)

        val boxOfficeTitleTextView: TextView
            get() = itemView.findViewById(R.id.boxOfficeTitleTextView)

        val boxOfficeWeekEndTextView: TextView
            get() = itemView.findViewById(R.id.boxOfficeWeekEndTextView)

        val boxOfficeImageView: ImageView
            get() = itemView.findViewById(R.id.boxOfficeImageView)

        val boxOfficeConstrainLayout: ConstraintLayout
            get() = itemView.findViewById(R.id.boxOfficeConstrainLayout)
    }
}