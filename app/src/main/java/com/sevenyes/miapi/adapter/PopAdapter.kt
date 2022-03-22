package com.sevenyes.miapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sevenyes.miapi.R
import com.sevenyes.miapi.model.Pop
import com.squareup.picasso.Picasso

class PopAdapter (var trackList : List<Pop> = listOf(),
    private val onPopClick : (pop : Pop) -> Unit)
    : RecyclerView.Adapter<PopHolder>()  {
    //
    fun  updatePop(newTrackList : List<Pop>){
        trackList = newTrackList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_item,parent,false)
        return PopHolder(view,onPopClick)
    }

    override fun onBindViewHolder(holder: PopHolder, position: Int) {
        holder.bind(trackList[position])
    }

    override fun getItemCount(): Int {
        return trackList.size
    }
}

class PopHolder(itemView: View,
    private val onPopClick: (pop: Pop) -> Unit) : RecyclerView.ViewHolder(itemView)
{
    private val artImage : ImageView = itemView.findViewById(R.id.art_image)
    private val song : TextView = itemView.findViewById(R.id.song)
    private val artist : TextView = itemView.findViewById(R.id.artist)
    private val price : TextView = itemView.findViewById(R.id.price)

    fun bind (pop : Pop)
    { song.text = pop.trackName
      artist.text = pop.artistName
      price.text = pop.trackPrice.toString()
      itemView.setOnClickListener {
          onPopClick(pop)
      }
      Picasso.get().load(pop.artworkUrl60)
          .resize(120,120)
          .into(artImage)
    }
}