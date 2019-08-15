package com.example.marveldirectory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.marveldirectory.R
import com.example.marveldirectory.data.entity.characters.comics.CharacterComicResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_comic_item.view.*

const val MAX_DATA_SIZE = 3

class CharacterComicAdapter : RecyclerView.Adapter<CharacterComicAdapter.ViewHolder>() {

    private var data: List<CharacterComicResult> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate((R.layout.character_comic_item), parent, false))
    }

    override fun getItemCount(): Int {
        return if (data.size > 3) {
            MAX_DATA_SIZE
        } else {
            data.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setComic(comics: List<CharacterComicResult>) {
        data = comics
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(comics: CharacterComicResult) {
            itemView.comicTitle.text = comics.title
            val image = comics.thumbnail.path + "." + comics.thumbnail.extension
            Picasso.get()
                .load(image)
                .placeholder(R.drawable.progress_animation)
                .into(itemView.comicPoster)
        }

    }

}