package com.example.marveldirectory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.marveldirectory.R
import com.example.marveldirectory.data.entity.characters.CharactersResults
import com.example.marveldirectory.data.entity.characters.comic.CharacterComicResult
import com.example.marveldirectory.fragment.CharacterFragmentDirections
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_comic_item.view.*
import java.lang.Exception

const val MAX_DATA_SIZE = 3

class CharacterComicAdapter : RecyclerView.Adapter<CharacterComicAdapter.ViewHolder>() {

    private var data: List<CharacterComicResult> = emptyList()
    private lateinit var specificCharacter: CharactersResults
    private var comicDataSize = 3
    private var totalComicSize = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate((R.layout.character_comic_item), parent, false))
    }

    override fun getItemCount(): Int {
        return comicDataSize
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data.isNotEmpty())
            holder.bind(data[position], data.size, position, specificCharacter, totalComicSize)
    }

    fun setComic(
        comics: List<CharacterComicResult>,
        character: CharactersResults,
        total: Int
    ) {
        data = comics
        totalComicSize = total
        specificCharacter = character
        comicDataSize =
            if (data.size > 3) {
                MAX_DATA_SIZE
            } else {
                data.size
            }
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            comics: CharacterComicResult,
            comicSize: Int,
            position: Int,
            specificCharacter: CharactersResults,
            totalComicSize: Int
        ) {
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
            if (position == 2 && comicSize > 3) {
                itemView.viewMoreComics.visibility = View.VISIBLE
                itemView.viewMoreComics.setOnClickListener {
                    val nextAction = CharacterFragmentDirections.actionDestinationCharacterToDestinationComic(specificCharacter, comics, totalComicSize)
                    nextAction.character = specificCharacter
                    nextAction.comic = comics
                    nextAction.comicSize = totalComicSize
                    Navigation.findNavController(it).navigate(nextAction)
                }
            }
        }

    }

}