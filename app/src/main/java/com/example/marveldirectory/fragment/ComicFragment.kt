package com.example.marveldirectory.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.marveldirectory.R
import com.example.marveldirectory.adapter.ComicsAdapter
import com.example.marveldirectory.data.entity.characters.comic.CharacterComicResult
import com.example.marveldirectory.viewmodel.ComicsViewModel
import kotlinx.android.synthetic.main.fragment_comic.*

class ComicFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comic, container, false)
    }

    private lateinit var comicsViewModel: ComicsViewModel
    private lateinit var adapter: ComicsAdapter
    private lateinit var comic: CharacterComicResult

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val safeArgs = ComicFragmentArgs.fromBundle(it)
            comic = safeArgs.comics
        }

        comicsViewModel = ViewModelProviders.of(this)
            .get(ComicsViewModel::class.java)

    }
}
