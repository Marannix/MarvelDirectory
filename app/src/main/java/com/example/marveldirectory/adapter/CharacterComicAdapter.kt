package com.example.marveldirectory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.marveldirectory.R
import com.example.marveldirectory.data.entity.characters.comics.CharacterComicResult
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_comic_item.view.*
import java.lang.Exception

const val MAX_DATA_SIZE = 3

class CharacterComicAdapter : RecyclerView.Adapter<CharacterComicAdapter.ViewHolder>() {

    private var data: List<CharacterComicResult> = emptyList()
    private var comicDataSize = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate((R.layout.character_comic_item), parent, false))
    }

    override fun getItemCount(): Int {
        return comicDataSize
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data.isNotEmpty())
            holder.bind(data[position])
    }

    fun setComic(comics: List<CharacterComicResult>) {
        data = comics
        comicDataSize =
            if (data.size > 3) {
                MAX_DATA_SIZE
            } else {
                data.size
            }
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(comics: CharacterComicResult) {
            itemView.comicTitle.text = comics.title
            val image = comics.thumbnail.path + "." + comics.thumbnail.extension
            Picasso.get()
                .load(image)
                .placeholder(R.drawable.progress_animation)
                .into(itemView.comicPoster, object : Callback {
                    override fun onSuccess() {
                        if (itemView.hourglass != null) {
                            itemView.hourglass.visibility = View.GONE
                        }
                    }

                    override fun onError(e: Exception?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
        }

    }

}