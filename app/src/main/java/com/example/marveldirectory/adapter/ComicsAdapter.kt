package com.example.marveldirectory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marveldirectory.R
import com.example.marveldirectory.data.entity.characters.comic.CharacterComicResult
import com.example.marveldirectory.data.network.NetworkState
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_of_character_comics_item.view.*

class ComicsAdapter(private val retry: () -> Unit) :
    PagedListAdapter<CharacterComicResult, RecyclerView.ViewHolder>(ComicsResultsDiffCallback) {

    private var state = NetworkState.LOADING

    companion object {
        val ComicsResultsDiffCallback = object : DiffUtil.ItemCallback<CharacterComicResult>() {
            override fun areItemsTheSame(oldItem: CharacterComicResult, newItem: CharacterComicResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterComicResult, newItem: CharacterComicResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ComicsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return (holder as ComicsViewHolder).bind(getItem(position)!!)
    }

    class ComicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(comics : CharacterComicResult) {
            itemView.comicName.text = comics.title
            val poster = comics.thumbnail.path + "." + comics.thumbnail.extension
            Picasso.get().load(poster).into(itemView.comicPoster)
        }

        companion object {
            fun create(parent: ViewGroup) : ComicsViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_of_character_comics_item, parent, false)
                return ComicsViewHolder(view)
            }
        }
    }

    fun setState( state: NetworkState) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

}