package com.confessionsearchapptest.release1

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.confessionsearchapptest.release1.data.notes.Notes
import com.confessionsearchapptest.release1.databinding.ActivityMainBinding
import com.confessionsearchapptest.release1.ui.NotesActivity.NotesComposeActivity
import com.confessionsearchapptest.release1.ui.NotesActivity.NotesFragment

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
                    R.id.navigation_notes, R.id.navigation_home, R.id.navigation_bible,R.id.navigation_help
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (Configuration.UI_MODE_NIGHT_MASK and resources.configuration.uiMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                var restart = Intent(context, MainActivity::class.java)
                Log.i("ConfigChange", "Restarting Actviity due to UI Change")
                finish()
                startActivity(restart)
            }

            Configuration.UI_MODE_NIGHT_YES -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                var restart = Intent(context, MainActivity::class.java)
                Log.i("ConfigChange", "Restarting Actviity due to UI Change")
                finish()
                startActivity(restart)
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun NewNote(view: View?) {
        val intent = Intent(context, NotesComposeActivity::class.java)
        intent.putExtra("activity_ID", NotesFragment.ACTIVITY_ID)
        startActivity(intent)

    }

    override fun onBackPressed() {
        this.finish()
    }
    companion object {
        var notesArrayList = ArrayList<Notes>()
    }
}

