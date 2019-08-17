package com.example.marveldirectory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marveldirectory.R
import com.example.marveldirectory.adapter.CharacterComicAdapter
import com.example.marveldirectory.data.entity.characters.CharactersResults
import com.example.marveldirectory.data.entity.characters.comics.CharacterComicResult
import com.example.marveldirectory.data.entity.characters.comics.CharacterComicThumbnail
import com.example.marveldirectory.repository.CharactersRepository
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.character_comic.*
import kotlinx.android.synthetic.main.character_header.*
import kotlinx.android.synthetic.main.character_summary.*
import kotlinx.android.synthetic.main.fragment_character.*
import java.lang.Exception

class CharacterFragment : Fragment() {

    private val disposables = CompositeDisposable()
    private val adapter = CharacterComicAdapter()

    private lateinit var character: CharactersResults

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val safeArgs = CharacterFragmentArgs.fromBundle(it)
            character = safeArgs.character
        }

        bind()
    }

    private fun removeToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    private fun showToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

    private fun bind() {
        removeToolbar()
        setupHeader()
        setSelectedCharacterSummary()
        setupComicAdapter()
        loadComics()
    }

    private fun setupComicAdapter() {
        characterComicRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        characterComicRecyclerView.adapter = adapter
    }

    private fun setupHeader() {
        setSelectedCharacterName()
        setSelectedCharacterImage()
    }

    private fun setSelectedCharacterName() {
        selectedCharacterName.text = character.name
    }

    private fun setSelectedCharacterPoster(results: List<CharacterComicResult>) {

        val image: String = if (results.isNotEmpty()) {
            results[0].thumbnail.path + "." + results[0].thumbnail.extension
        } else {
            character.thumbnail.path + "." + character.thumbnail.extension
        }

        Picasso.get().load(image).into(chosenCharacterPoster, object: Callback {
            override fun onSuccess() {
                if (characterHeaderPosterLoading != null) {
                    characterHeaderPosterLoading.visibility = View.GONE
                }
            }

            override fun onError(e: Exception?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }

    private fun setSelectedCharacterImage() {
        val image = character.thumbnail.path + "." + character.thumbnail.extension
        Picasso.get().load(image).into(chosenCharacterImage)
    }

    private fun setSelectedCharacterSummary() {
        if (character.description == "") {
            character_summary.visibility = View.GONE
            return
        }
        selectedCharacterDescription.text = character.description
    }

    private fun loadComics() {
        val disposable = CharactersRepository(requireContext()).fetchCharacterComics(character.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onRetrieveCharactersComicSuccess(it.characterComicData.results) },
                { onRetrieveCharactersComicError(it.message) }
            )
        disposables.add(disposable)
    }

    private fun onRetrieveCharactersComicSuccess(results: List<CharacterComicResult>) {
        setSelectedCharacterPoster(results)
        adapter.setComic(results)
    }

    private fun onRetrieveCharactersComicError(errorMessage: String?) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onStop() {
        showToolbar()
        disposables.dispose()
        super.onStop()
    }
}
