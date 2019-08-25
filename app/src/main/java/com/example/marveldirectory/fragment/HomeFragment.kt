package com.example.marveldirectory.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marveldirectory.R
import com.example.marveldirectory.adapter.CharactersAdapter
import com.example.marveldirectory.data.network.NetworkState
import com.example.marveldirectory.viewmodel.CharactersViewModel
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename to CharactersFragment
class HomeFragment : Fragment() {

    private lateinit var charactersViewModel: CharactersViewModel
    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        charactersViewModel = ViewModelProviders.of(this)
            .get(CharactersViewModel::class.java)

        bindUI()
    }

    private fun bindUI() {
        updateToolbar()
        setUpdateAdapter()
        initState()
    }

    private fun setUpdateAdapter() {
        adapter = CharactersAdapter { charactersViewModel.retry() }

        //TODO: If using tablet set spanCount to 7?
        charactersRecyclerView.layoutManager = GridLayoutManager(context, 3)
        charactersRecyclerView.adapter = adapter
        charactersViewModel.charactersList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun initState() {
        errorAnimation.setOnClickListener {
            charactersViewModel.retry()
        }
        charactersViewModel.getState().observe(this, Observer { state ->
            heroLoading.visibility =
                if (charactersViewModel.listIsEmpty() && state == NetworkState.LOADING) View.VISIBLE else View.GONE
            errorAnimation.visibility =
                if (charactersViewModel.listIsEmpty() && state == NetworkState.ERROR) View.VISIBLE else View.INVISIBLE
            if (!charactersViewModel.listIsEmpty()) {
                adapter.setState(state ?: NetworkState.DONE)
                totalCharacterCount.text = "${charactersViewModel.getTotal()} characters"

            }
        })
    }

    private fun updateToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Marvel Characters"
    }

}
