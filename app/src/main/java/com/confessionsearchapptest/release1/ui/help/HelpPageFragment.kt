package com.confessionsearchapptest.release1.ui.help

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.confessionsearchapptest.release1.BuildConfig
import com.confessionsearchapptest.release1.MainActivity
import com.confessionsearchapptest.release1.R
import kotlinx.coroutines.MainScope
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class HelpPageFragment : Fragment() {

    val newLine = "<br>"

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_help_page, container, false)
        var searchTabTV = view.findViewById<TextView>(R.id.searchTabLabel)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            searchTabTV.text = Html.fromHtml(
                (getString(R.string.search_tab) + newLine + getString(R.string.help_searchTab_pgh1)),
                Html.FROM_HTML_MODE_COMPACT
            )
        }
        else
            searchTabTV.text = Html.fromHtml(getString(R.string.search_tab) + newLine + getString(R.string.help_searchTab_pgh1))
        var bibleTabTV = view.findViewById<TextView>(R.id.bibleTabLabel)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            bibleTabTV.text = Html.fromHtml(
                (getString(R.string.bible_tab_HelpLabel) + newLine + getString(R.string.bible_tab_helpPgh)),
                Html.FROM_HTML_MODE_COMPACT
            )
        }
        else
            bibleTabTV.text=Html.fromHtml(getString(R.string.bible_tab_HelpLabel) + newLine + getString(R.string.bible_tab_helpPgh))
        var notesTabTV = view.findViewById<TextView>(R.id.notesTabLabel)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notesTabTV.text = Html.fromHtml(
                (getString(R.string.notes_tab_helpLabel) + newLine + getString(R.string.notes_tab_helpPgh)),
                Html.FROM_HTML_MODE_COMPACT
            )

        }
        else
            notesTabTV.text = Html.fromHtml(((getString(R.string.notes_tab_helpLabel) + newLine + getString(R.string.notes_tab_helpPgh))))
        var sourcesLabelTV = view.findViewById<TextView>(R.id.sourcesTabLabel)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sourcesLabelTV.text =
                Html.fromHtml(
                    (getString(R.string.sources_tab) + newLine + getString(R.string.copyright_disclaimer)),
                    Html.FROM_HTML_MODE_COMPACT
                )
        }
        else
            sourcesLabelTV.text = Html.fromHtml((getString(R.string.sources_tab) + newLine + getString(R.string.copyright_disclaimer)))
        var ratingsTV = view.findViewById<TextView>(R.id.ratingsTV)
        ratingsTV.text = getString(R.string.ratings_suggestion)
        ratingsTV.setOnClickListener(ratingsOnClickListener)
        var emailDevTV = view.findViewById<TextView>(R.id.emailDevTV)
        emailDevTV.text = getString(R.string.emailDev)
        emailDevTV.setOnClickListener(emailOnClickListener)
        var versionTV = view.findViewById<TextView>(R.id.versionInfoTV)
        versionTV.text = "Version #: ${MainActivity.versionName}"

        return view

    }

    var ratingsOnClickListener = View.OnClickListener {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=${MainActivity.appName}")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=${MainActivity.appName}")
                )
            )
        }
    }
    var emailOnClickListener = View.OnClickListener {
        var subject: String? =
            "Feature report or Bug Request for ${getString(R.string.app_name)} Version:${MainActivity.versionName}"
        composeEmail(subject!!)
    }

    @JvmOverloads
    fun composeEmail(subject: String = "Feature report or Bug Request for ${getString(R.string.app_name)} Version:${MainActivity.versionName}") {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.devEmail)))
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        startActivity(intent)
        if (intent.resolveActivity(MainActivity.appContext!!.packageManager) != null) {
            startActivity(intent)
        }
    }


    fun runAnimation(textView: TextView) {
        val animationA = AnimationUtils.loadAnimation(requireContext(), R.anim.animate_card_enter)
        animationA.scaleCurrentDuration(2f)
        animationA.reset()
        textView.clearAnimation()
        textView.startAnimation(animationA)
    }

}