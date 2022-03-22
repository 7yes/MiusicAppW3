package com.sevenyes.miapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sevenyes.miapi.R
import com.sevenyes.miapi.model.Rock
import com.squareup.picasso.Picasso

class RockAdapter(
    var trackList : List<Rock> = listOf(),
    private val onRockClick : (rock : Rock) ->Unit
) : RecyclerView.Adapter<RockHolder>() {

    fun updateRock(newTrackList: List<Rock>){
        trackList = newTrackList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RockHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_item, parent,false)
        return RockHolder(view,onRockClick)
    }

    override fun onBindViewHolder(holder: RockHolder, position: Int) {
        holder.bind(trackList[position])
    }

    override fun getItemCount(): Int {
        return trackList.size
    }
}


class RockHolder (val viewItem : View,
                  private val  onRockClick: (rock: Rock) -> Unit
    ) : RecyclerView.ViewHolder(viewItem) {

    private val image : ImageView = viewItem.findViewById(R.id.art_image)
    private val song : TextView = viewItem.findViewById(R.id.song)
    private val  artist : TextView = viewItem.findViewById(R.id.artist)
    private val price = viewItem.findViewById<TextView>(R.id.price) // other way to declared the type of val

    fun bind(rock: Rock) {
        song.text = rock.trackName
        artist.text = rock.artistName
        price.text = rock.trackPrice.toString()
        viewItem.setOnClickListener {
            onRockClick(rock)
        }
        Picasso.get().load(rock.artworkUrl60)
            .resize(120,120).centerCrop()
            .into(image)
    }

}