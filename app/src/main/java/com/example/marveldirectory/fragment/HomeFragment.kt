package com.example.marveldirectory.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.marveldirectory.R
import com.example.marveldirectory.adapter.CharacterAdapter
import com.example.marveldirectory.repository.CharactersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val disposables = CompositeDisposable()
    private val adapter = CharacterAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterRecyclerView.layoutManager = GridLayoutManager(context, 3)
        characterRecyclerView.adapter = adapter
        getCharacters()
    }

    private fun getCharacters() {
        val disposable = CharactersRepository().fetchCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.setData(it.dataEntry.results)
            },
                {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                    adapter.setFakeData()
                })

        disposables.add(disposable)
    }

    override fun onStop() {
        disposables.dispose()
        super.onStop()
    }
}
