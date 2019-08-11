package com.example.marveldirectory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.marveldirectory.R
import com.example.marveldirectory.data.entity.characters.CharactersResults
import com.example.marveldirectory.data.entity.characters.CharactersThumbnail
import com.example.marveldirectory.fragment.HomeFragmentDirections
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_item.view.*

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    private var characters: List<CharactersResults> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate((R.layout.character_item), parent, false))
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    fun setData(results: List<CharactersResults>) {
        characters = results
        this.notifyDataSetChanged()
    }

    fun setFakeData() {
        characters =
            listOf(
                CharactersResults(
                    1343, "A-Star", "",
                    CharactersThumbnail("https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg", ".jpg")
                ),
                CharactersResults(
                    1343, "A-Star", "",
                    CharactersThumbnail("https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", ".jpg")
                ),
                CharactersResults(
                    1343, "A-Star", "",
                    CharactersThumbnail("https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", ".jpg")
                ),
                CharactersResults(
                    1343, "A-Star", "",
                    CharactersThumbnail("https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", ".jpg")
                ),
                CharactersResults(
                    1343, "A-Star", "",
                    CharactersThumbnail("https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", ".jpg")
                )
            )
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: CharactersResults) {
            itemView.characterName.text = character.name
            val image = character.thumbnail.path + "." + character.thumbnail.extension
            Picasso.get().load(image).resize(335,335).into(itemView.characterImage)
            itemView.characterLayout.setOnClickListener {
                val nextAction = HomeFragmentDirections.charactersToCharacterAction(character)
                nextAction.character = character
                Navigation.findNavController(it).navigate(nextAction)
            }
        }
    }
}