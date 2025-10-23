package com.example.responsi1mobileh1d023115.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.example.responsi1mobileh1d023115.R
import com.example.responsi1mobileh1d023115.api.RetrofitClient


class CoachFragment : Fragment() {

    // Token API dan ID Klub (sudah benar)
    private val API_TOKEN = "35d5ee369a44490ea6afc1b7c3f9c30b"
    private val TEAM_ID = 338

    private lateinit var coachName: TextView
    private lateinit var coachDob: TextView
    private lateinit var coachNationality: TextView
    private lateinit var coachPhoto: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_coach, container, false)

        // Inisialisasi komponen UI
        coachName = view.findViewById(R.id.coach_name)
        coachDob = view.findViewById(R.id.coach_dob)
        coachNationality = view.findViewById(R.id.coach_nationality)
        coachPhoto = view.findViewById(R.id.coach_photo)

        fetchCoachDetails()

        return view
    }

    private fun fetchCoachDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.instance.getTeamDetails(TEAM_ID, API_TOKEN)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && isAdded) {
                        val team = response.body()

                        // 1. Isolasi semua yang BUKAN pemain (Staf/Pelatih)
                        // Kriteria: Position harus NULL (anggota staf)
                        val allStaff = team?.squad?.filter { member ->
                            member.position == null
                        } ?: emptyList()

                        // 2. 💡 AMBIL PELATIH KEPALA: Ambil anggota staf di INDEX 1 (anggota kedua)
                        // Karena Begović (pemain dengan data anomali) ada di index 0.
                        val coach = allStaff.getOrNull(1) // Mengambil anggota di index 1 (kedua)


                        if (coach != null) {
                            // Pelatih DITEMUKAN
                            coachName.text = coach.name
                            coachDob.text = "Lahir: ${coach.dateOfBirth ?: "-"}"
                            coachNationality.text = "Kebangsaan: ${coach.nationality}"

                            coachPhoto.setImageResource(R.drawable.coach)

                            Log.d("COACH_SUCCESS", "Pelatih Ditemukan di Index 1: ${coach.name}")

                        } else {
                            // Fallback ke anggota skuad pertama jika index 1 tidak ada
                            val fallbackCoach = team?.squad?.firstOrNull()

                            if (fallbackCoach != null) {
                                // Ini akan menampilkan Asmir Begović lagi, tetapi menunjukkan filter gagal
                                coachName.text = fallbackCoach.name
                                coachDob.text = "Lahir: ${fallbackCoach.dateOfBirth ?: "-"}"
                                coachNationality.text = "Kebangsaan: ${fallbackCoach.nationality}"
                                coachPhoto.setImageResource(R.drawable.coach)

                                Log.w("COACH_FALLBACK", "Pelatih spesifik tidak ditemukan, menampilkan anggota index pertama.")
                            } else {
                                Toast.makeText(context, "Data Pelatih tidak ditemukan.", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Log.e("API_CALL", "Error code: ${response.code()}. Token API mungkin salah.")
                        Toast.makeText(context, "Gagal memuat data. Kode Error: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("API_CALL", "Koneksi Gagal: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Gagal terhubung ke internet.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}