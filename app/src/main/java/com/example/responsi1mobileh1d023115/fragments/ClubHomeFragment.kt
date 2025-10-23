package com.example.responsi1mobileh1d023115.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.responsi1mobileh1d023115.R
import com.example.responsi1mobileh1d023115.MainActivity

class ClubHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_club_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Ambil referensi komponen UI
        val clubLogoHeader: ImageView = view.findViewById(R.id.club_logo_header)
        val clubNameHeader: TextView = view.findViewById(R.id.club_name_header)
        val clubDescription: TextView = view.findViewById(R.id.club_description)

        // Ubah dari TextView ke CardView untuk tombol navigasi
        val cardBtnHistory: CardView = view.findViewById(R.id.card_btn_history)
        val cardBtnCoach: CardView = view.findViewById(R.id.card_btn_coach)
        val cardBtnSquad: CardView = view.findViewById(R.id.card_btn_squad)

        // 2. Isi konten statis
        clubNameHeader.text = getString(R.string.club_name_title)
        clubDescription.text = getString(R.string.club_profile_summary)
        clubLogoHeader.setImageResource(R.drawable.ic_leicester_logo)


        // 3. Implementasi Navigasi
        val mainActivity = activity as? MainActivity

        cardBtnHistory.setOnClickListener {
            mainActivity?.navigateTo(ClubHistoryFragment())
        }

        cardBtnCoach.setOnClickListener {
            mainActivity?.navigateTo(CoachFragment())
        }

        cardBtnSquad.setOnClickListener {
            mainActivity?.navigateTo(SquadFragment())
        }
    }
}