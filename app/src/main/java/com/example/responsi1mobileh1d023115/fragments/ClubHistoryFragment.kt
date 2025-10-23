package com.example.responsi1mobileh1d023115.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.responsi1mobileh1d023115.R
import android.text.Html

class ClubHistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_club_history, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val historyTitle: TextView = view.findViewById(R.id.history_title) // Asumsi Anda punya ID ini
        val historyText: TextView = view.findViewById(R.id.club_history_text)

        historyTitle.text = getString(R.string.history_title)

        @Suppress("DEPRECATION")
        historyText.text = Html.fromHtml(getString(R.string.leicester_history_content))
    }
}