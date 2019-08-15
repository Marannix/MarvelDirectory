package com.example.marveldirectory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marveldirectory.R
import com.example.marveldirectory.adapter.CharactersAdapter
import com.example.marveldirectory.data.entity.characters.CharactersResults
import com.example.marveldirectory.data.network.NetworkState
import com.example.marveldirectory.viewmodel.CharactersViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename to CharactersFragment
class HomeFragment : Fragment() {

    private lateinit var charactersViewModel: CharactersViewModel
    private lateinit var adapter: CharactersAdapter

    private val disposables = CompositeDisposable()
//    private val adapter = CharactersAdapter()

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
//        loadCharacters()
    }

    private fun setUpdateAdapter() {
        adapter = CharactersAdapter { charactersViewModel.retry() }
        charactersRecyclerView.layoutManager = GridLayoutManager(context, 3)
        charactersRecyclerView.adapter = adapter
        charactersViewModel.charactersList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun initState() {
        errorTextView.setOnClickListener { charactersViewModel.retry() }
        charactersViewModel.getState().observe(this, Observer { state ->
            progressBar.visibility =
                if (charactersViewModel.listIsEmpty() && state == NetworkState.LOADING) View.VISIBLE else View.GONE
            errorTextView.visibility =
                if (charactersViewModel.listIsEmpty() && state == NetworkState.ERROR) View.VISIBLE else View.GONE
            if (!charactersViewModel.listIsEmpty()) {
                adapter.setState(state ?: NetworkState.DONE)
            }
        })
    }


//    private fun loadCharacters() {
//        val disposable = CharactersRepository(requireContext()).fetchCharacters()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    onRetrieveCharactersSuccess(it.charactersData.results)
//                    CharactersRepository(requireContext()).persistFetchedCharacters(it.charactersData.results)
//                },
//                { onRetrieveCharactersError(it.message) }
//            )
//
//        val check = CharactersRepository(requireContext()).getAllCharacters()[1].name
//        Log.d("fail", check)
//        disposables.add(disposable)
//    }

    private fun onRetrieveCharactersSuccess(results: List<CharactersResults>) {
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
