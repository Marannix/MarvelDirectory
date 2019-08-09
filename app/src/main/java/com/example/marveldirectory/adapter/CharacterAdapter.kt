package com.example.marveldirectory.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marveldirectory.R
import com.example.marveldirectory.data.entity.characters.Results
import com.example.marveldirectory.data.entity.characters.Thumbnail
import com.example.marveldirectory.fragment.HomeFragment
import com.example.marveldirectory.fragment.HomeFragmentDirections
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_item.view.*

class CharacterAdapter: RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var characters: List<Results> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(parent.context).inflate((R.layout.character_item), parent, false))
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    fun setData(results: List<Results>) {
        characters = results
        this.notifyDataSetChanged()
    }

    fun setFakeData() {
        characters =
            listOf(
                Results(1343, "A-Star", "",
                    Thumbnail("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", ".jpg")),
                Results(1343, "A-Star", "",
                    Thumbnail("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", ".jpg")),
                Results(1343, "A-Star", "",
                    Thumbnail("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", ".jpg")),
                Results(1343, "A-Star", "",
                    Thumbnail("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", ".jpg")),
                Results(1343, "A-Star", "",
                Thumbnail("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", ".jpg") )
            )
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: Results) {
            itemView.characterName.text = character.name
            Picasso.get().load("https://cdn.pixabay.com/photo/2017/02/07/16/47/kingfisher-2046453_960_720.jpg").into(itemView.characterImage)
            itemView.characterLayout.setOnClickListener {
                val nextAction = HomeFragmentDirections.homeToComicAction(character)
                nextAction.characters = character
                Navigation.findNavController(it).navigate(nextAction)
            }
        }
    }
}