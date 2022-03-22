package com.sevenyes.miapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sevenyes.miapi.R
import com.sevenyes.miapi.model.Classic
import com.squareup.picasso.Picasso

class ClassicAdapter(
    private var trackList : List<Classic> = listOf(),
    private val onClassicTrackClick: (classic : Classic) -> Unit
) : RecyclerView.Adapter<ClassicHolder>()  {

    fun updateClassic(newTrackList : List<Classic>) {
        trackList = newTrackList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassicHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_item, parent, false)
        return ClassicHolder(view,onClassicTrackClick)
    }

    override fun onBindViewHolder(holder: ClassicHolder, position: Int) {
        holder.bind(trackList[position])
    }

    override fun getItemCount(): Int {
        return trackList.size
    }
}

class ClassicHolder (
    itemView: View,
    private val onClassicTrackClick: (classic: Classic) -> Unit)
    : RecyclerView.ViewHolder(itemView){

    private val artImage : ImageView = itemView.findViewById(R.id.art_image)
    private val song : TextView = itemView.findViewById(R.id.song)
    private val artist : TextView = itemView.findViewById(R.id.artist)
    private val price : TextView =  itemView.findViewById(R.id.price)

    fun bind (classic : Classic){
        song.text = classic.trackName
        artist.text = classic.artistName
        price.text = classic.trackPrice.toString()
        Picasso.get().load(classic.artworkUrl60)
            .resize(120,120)
            .into(artImage)

        itemView.setOnClickListener {
            onClassicTrackClick(classic)
        }

    }
}