package com.confessionsearchapptest.release1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.confessionsearchapptest.release1.data.notes.Notes
import com.confessionsearchapptest.release1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val navView: BottomNavigationView = binding.navView

            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_notes, R.id.navigation_home, R.id.navigation_bible
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
    override fun onBackPressed() {

        this.finish()
    }
    companion object {
        var notesArrayList = ArrayList<Notes>()
    }
}

