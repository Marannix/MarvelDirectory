package com.example.marveldirectory.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.marveldirectory.R
import com.example.marveldirectory.data.database.DatabaseHelper
import com.example.marveldirectory.data.database.MarvelDatabase
import com.example.marveldirectory.data.entity.characters.CharactersDataDao
import com.example.marveldirectory.repository.CharactersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        DatabaseHelper(this).retrieveDatabase()

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.destination_home -> {
                    showBottomNav()
                }
                else -> hideBottomNav()
            }
        }

        setupBottomNavMenu(navController)
    }


    private fun setupBottomNavMenu(navController: NavController) {
        bottom_nav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val navigated = NavigationUI.onNavDestinationSelected(item, navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    private fun showBottomNav() {
        bottom_nav.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        bottom_nav.visibility = View.GONE

    }

//    // for landscape
//    private fun setupActionBar(navController: NavController) {
//        NavigationUI.setupActionBarWithNavController(this, navController, activity_main)
//    }

}
