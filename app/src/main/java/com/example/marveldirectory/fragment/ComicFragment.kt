package com.example.marveldirectory.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager

import com.example.marveldirectory.R
import com.example.marveldirectory.adapter.ComicsAdapter
import com.example.marveldirectory.data.entity.characters.CharactersResults
import com.example.marveldirectory.data.entity.characters.comic.CharacterComicResult
import com.example.marveldirectory.data.network.NetworkState
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
    private lateinit var character: CharactersResults
    private lateinit var comic: CharacterComicResult
    private var comicSize: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val safeArgs = ComicFragmentArgs.fromBundle(it)
            character = safeArgs.character
            comic = safeArgs.comic
            comicSize = safeArgs.comicSize
        }

        comicsViewModel = ViewModelProviders.of(this)
            .get(ComicsViewModel::class.java)

        comicsViewModel.initViewModel(character, comic)

        totalComicCount.text = "$comicSize comics"
        bindUI()
    }

    private fun bindUI() {
        updateToolbar()
        showToolbar()
        setAdapter()
    }


    private fun updateToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "${character.name} Comics"
    }

    private fun showToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

    private fun setAdapter() {
        adapter = ComicsAdapter { comicsViewModel.retry() }

        listOfComicsRecyclerView.adapter = adapter
        listOfComicsRecyclerView.layoutManager = GridLayoutManager(context, 3)

        comicsViewModel.comicsList.observe(this, Observer {
            adapter.submitList(it)
        })

    }

    private fun initState() {
        comicsViewModel.getState().observe(
            this, Observer {
                if (!comicsViewModel.listIsEmpty()) {
                    adapter.setState(it ?: NetworkState.DONE)
                }
            }
        )
    }

}
