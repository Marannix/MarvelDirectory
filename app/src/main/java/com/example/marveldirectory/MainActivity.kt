package com.example.marveldirectory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.marveldirectory.data.network.MarvelApiService
import com.example.marveldirectory.data.network.response.CharactersResponse
import com.example.marveldirectory.repository.CharactersRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCharacters()
    }

    private fun getCharacters() {
        val disposable = CharactersRepository().fetchCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                textview3.text = it.dataEntry.results[1].name
            },
                {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
                        .show()
                    Log.d("ouch", it.message!!)
            })

        disposables.add(disposable)
    }

    override fun onStop() {
        disposables.dispose()
        super.onStop()
    }
}
