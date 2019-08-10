package com.example.marveldirectory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.marveldirectory.R
import com.example.marveldirectory.data.entity.characters.Results
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
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

    private fun bind() {
        removeToolbar()
        setSelectedCharacterImage()
        setSelectedCharacterSummary()
        setSelectedCharacterPoster()
    }

    private fun removeToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    private fun showToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

    private fun setSelectedCharacterPoster() {
        val image = character.thumbnail.path + "." + character.thumbnail.extension
        Picasso.get().load(image).into(chosenCharacterPoster)
    }

    private fun setSelectedCharacterImage() {
        val image = character.thumbnail.path + "." + character.thumbnail.extension
        Picasso.get().load(image).resize(335, 335).into(chosenCharacterImage)
    }

    private fun setSelectedCharacterSummary() {
        if (character.description == "") {
            character_summary.visibility = View.GONE
            return
        }
        selectedCharacterDescription.text = character.description
    }

    override fun onStop() {
        showToolbar()
        super.onStop()
    }
}
