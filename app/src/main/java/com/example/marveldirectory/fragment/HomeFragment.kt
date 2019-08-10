package com.example.marveldirectory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marveldirectory.R
import com.example.marveldirectory.adapter.CharactersAdapter
import com.example.marveldirectory.data.entity.characters.Results
import com.example.marveldirectory.repository.CharactersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename to CharactersFragment
class HomeFragment : Fragment() {

    private val disposables = CompositeDisposable()
    private val adapter = CharactersAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        updateToolbar()
        setUpdateAdapter()
        loadCharacters()
    }

    private fun setUpdateAdapter() {
        charactersRecyclerView.layoutManager = GridLayoutManager(context, 3)
        charactersRecyclerView.adapter = adapter
    }

    private fun loadCharacters() {
        val disposable = CharactersRepository().fetchCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onRetrieveCharactersSuccess(it.dataEntry.results) },
                { onRetrieveCharactersError(it.message) }
            )

        disposables.add(disposable)
    }

    private fun onRetrieveCharactersSuccess(results: List<Results>) {
        adapter.setData(results)
    }

    private fun onRetrieveCharactersError(errorMessage: String?) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
            .show()
        adapter.setFakeData()
    }

    private fun updateToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Marvel Characters"
    }

    override fun onStop() {
        disposables.dispose()
        super.onStop()
    }
}
