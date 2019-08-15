package com.example.marveldirectory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marveldirectory.R
import com.example.marveldirectory.data.entity.characters.CharactersResults
import com.example.marveldirectory.data.entity.characters.CharactersThumbnail
import com.example.marveldirectory.data.network.NetworkState
import com.example.marveldirectory.fragment.HomeFragmentDirections
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_item.view.*
import kotlinx.android.synthetic.main.characters_list_footer.view.*
import java.lang.Exception

private const val DATA_VIEW_TYPE = 1
private const val FOOTER_VIEW_TYPE = 2

class CharactersAdapter(private val retry: () -> Unit) :
    PagedListAdapter<CharactersResults, RecyclerView.ViewHolder>(CharactersResultsDiffCallback) {


    private var state = NetworkState.LOADING

    private var characters: List<CharactersResults> = emptyList()

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(LayoutInflater.from(parent.context).inflate((R.layout.character_item), parent, false))
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) CharactersViewHolder.create(parent) else ListFooterViewHolder.create(
            retry,
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as CharactersViewHolder).bind(getItem(position)!!)
        else (holder as ListFooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    // TODO: DOUBLE CHECK THIS
    companion object {
        val CharactersResultsDiffCallback = object : DiffUtil.ItemCallback<CharactersResults>() {
            override fun areItemsTheSame(oldItem: CharactersResults, newItem: CharactersResults): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharactersResults, newItem: CharactersResults): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == NetworkState.LOADING || state == NetworkState.ERROR)
    }

    fun setState( state: NetworkState) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(characters[position])
//    }

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

    class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: CharactersResults) {
            itemView.characterName.text = character.name
            val image = character.thumbnail.path + "." + character.thumbnail.extension
            Picasso.get().load(image).into(itemView.characterImage, object: Callback {
                override fun onSuccess() {
                  if (itemView.characterImageProgress != null) {
                      itemView.characterImageProgress.visibility = View.GONE
                  }
                }

                override fun onError(e: Exception?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
            itemView.characterLayout.setOnClickListener {
                val nextAction = HomeFragmentDirections.charactersToCharacterAction(character)
                nextAction.character = character
                Navigation.findNavController(it).navigate(nextAction)
            }
        }

        companion object {
            fun create(parent: ViewGroup): CharactersViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.character_item, parent, false)
                return CharactersViewHolder(view)

            }
        }
    }


    class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(status: NetworkState?) {
            itemView.progressBar.visibility = if (status == NetworkState.LOADING) VISIBLE else View.INVISIBLE
            itemView.errorTextView.visibility = if (status == NetworkState.ERROR) VISIBLE else View.INVISIBLE
        }

        companion object {
            fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.characters_list_footer, parent, false)
                view.errorTextView.setOnClickListener { retry() }
                return ListFooterViewHolder(view)
            }
        }
    }
}