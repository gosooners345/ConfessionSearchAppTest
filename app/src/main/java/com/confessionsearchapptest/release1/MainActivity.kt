package com.confessionsearchapptest.release1

import android.app.Application
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.confessionsearchapptest.release1.data.documents.documentDBClassHelper
import com.confessionsearchapptest.release1.data.notes.Notes
import com.confessionsearchapptest.release1.databinding.ActivityMainBinding
import com.confessionsearchapptest.release1.searchhandlers.SearchHandler
import com.confessionsearchapptest.release1.ui.NotesActivity.NotesComposeActivity
import com.confessionsearchapptest.release1.ui.NotesActivity.NotesFragment


class MainActivity : AppCompatActivity() {

    /*private lateinit var binding: ActivityMainBinding*/
public val context : Context = this

//var documentDBHelper : documentDBClassHelper? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//documentDBHelper = documentDBClassHelper(this)



       try {
           var binding = ActivityMainBinding.inflate(layoutInflater)
           setContentView(binding.root)

           val navView: BottomNavigationView = binding.navView

           val navController = findNavController(R.id.nav_host_fragment_activity_main)
           // Passing each menu ID as a set of Ids because each
           // menu should be considered as top level destinations.
           val appBarConfiguration = AppBarConfiguration(
               setOf(
                   R.id.navigation_home, R.id.navigation_notes,R.id.navigation_bible
               )
           )
           setupActionBarWithNavController(navController, appBarConfiguration)
           navView.setupWithNavController(navController)
       } catch (ex: Exception) {
           ex.printStackTrace()
           Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
       }

    }
    fun NewNote(view: View?) {
        val intent = Intent(context, NotesComposeActivity::class.java)
        intent.putExtra("activity_ID", NotesFragment.ACTIVITY_ID)
        startActivity(intent)

    }

    companion object {
        var notesArrayList = ArrayList<Notes>()


    }


    }

