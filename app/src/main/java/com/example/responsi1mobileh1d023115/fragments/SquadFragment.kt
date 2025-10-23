package com.example.responsi1mobileh1d023115.fragments
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.responsi1mobileh1d023115.R
import com.example.responsi1mobileh1d023115.adapter.PlayerClickListener
import com.example.responsi1mobileh1d023115.model.SquadMember
import com.example.responsi1mobileh1d023115.adapter.PlayerAdapter
import com.example.responsi1mobileh1d023115.api.RetrofitClient

class SquadFragment : Fragment(), PlayerClickListener {

    private val API_TOKEN = "35d5ee369a44490ea6afc1b7c3f9c30b"
    private val TEAM_ID = 338

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_squad, container, false)

        recyclerView = view.findViewById(R.id.squad_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchTeamSquad()

        return view
    }

    private fun fetchTeamSquad() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.instance.getTeamDetails(TEAM_ID, API_TOKEN)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && isAdded) {
                        val team = response.body()

                        // --- FILTER FINAL UNTUK PEMAIN (Mengecualikan Staf/Pelatih) ---
                        val players = team?.squad
                            ?.filter { member ->
                                val role = member.role.orEmpty().uppercase()

                                // Pemain adalah anggota yang BUKAN staf
                                val isNotStaff = !(role.contains("COACH") ||
                                        role.contains("MANAGER") ||
                                        role.contains("STAFF"))

                                isNotStaff
                            } ?: emptyList()

                        Log.d("API_DEBUG", "Total anggota skuad (mentah): ${team?.squad?.size ?: 0}")
                        Log.d("API_DEBUG", "Jumlah pemain setelah filter (FINAL): ${players.size}")


                        if (players.isNotEmpty()) {
                            // 💡 PERUBAHAN 2: Kirim 'this' (Fragment ini) sebagai listener ke Adapter
                            recyclerView.adapter = PlayerAdapter(players, this@SquadFragment)
                        } else {
                            Log.e("API_ERROR", "Filter pemain gagal menemukan pemain.")
                            Toast.makeText(context, "Data pemain tidak ditemukan.", Toast.LENGTH_LONG).show()
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

    override fun onPlayerClicked(player: SquadMember) {
        val positionDisplay = player.position ?: "Posisi Tidak Diketahui"

        val message = "Tanggal Lahir: ${player.dateOfBirth ?: "-"}\n" +
                "Kebangsaan: ${player.nationality}\n" +
                "Posisi: ${positionDisplay}"

        android.app.AlertDialog.Builder(requireContext())
            .setTitle(player.name)
            .setMessage(message)
            .setPositiveButton("Tutup") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}