package com.example.marveldirectory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.marveldirectory.R
import com.example.marveldirectory.data.entity.characters.Results
import com.example.marveldirectory.data.entity.characters.comics.CharacterComicResult
import com.example.marveldirectory.repository.CharactersRepository
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.character_header.*
import kotlinx.android.synthetic.main.character_summary.*
import kotlinx.android.synthetic.main.fragment_character.*

class CharacterFragment : Fragment() {

    private val disposables = CompositeDisposable()

    private lateinit var character: Results

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
        //setSelectedCharacterPoster()
        loadComics()
    }

    private fun setupHeader() {
        setSelectedCharacterName()
        setSelectedCharacterImage()
    }

    private fun setSelectedCharacterName() {
        selectedCharacterName.text = character.name
    }

//    private fun setSelectedCharacterPoster() {
//        val image = character.thumbnail.path + "." + character.thumbnail.extension
//        Picasso.get().load(image).into(chosenCharacterPoster)
//    }

    private fun setSelectedCharacterImage() {
        val image = character.thumbnail.path + "." + character.thumbnail.extension
        Picasso.get().load(image).resize(250, 250).into(chosenCharacterImage)
    }

    private fun setSelectedCharacterSummary() {
        if (character.description == "") {
            character_summary.visibility = View.GONE
            return
        }
        selectedCharacterDescription.text = character.description
    }

    private fun loadComics() {
        val disposable = CharactersRepository().fetchCharacterComics(character.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onRetrieveCharactersComicSuccess(it.characterComicEntry.results) },
                { onRetrieveCharactersComicError(it.message) }
            )
        disposables.add(disposable)
    }

    private fun onRetrieveCharactersComicSuccess(results: List<CharacterComicResult>) {
        Picasso.get().load(results[0].thumbnail.path + "." + results[0].thumbnail.extension  ).into(chosenCharacterPoster)
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
