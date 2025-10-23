package com.example.responsi1mobileh1d023115.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.responsi1mobileh1d023115.R
import com.example.responsi1mobileh1d023115.model.SquadMember
import kotlin.random.Random


class PlayerAdapter(
    private val players: List<SquadMember>,
    private val listener: PlayerClickListener
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName: TextView = itemView.findViewById(R.id.player_name)
        val playerNationality: TextView = itemView.findViewById(R.id.player_nationality)
        val playerCard: CardView = itemView.findViewById(R.id.player_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.playerName.text = player.name
        holder.playerNationality.text = player.nationality

        val positionString = player.position.orEmpty().uppercase()

        // --- Tentukan warna berdasarkan posisi ---
        val determinedColorResId = when (positionString) {
            // Kategori 1: Kuning (Goalkeeper)
            "GOALKEEPER" -> R.color.yellow_goalkeeper

            // Kategori 2: Biru (Defender)
            "DEFENCE", "CENTER_BACK", "LEFT_BACK", "RIGHT_BACK", "FULL_BACK" -> R.color.blue_defense

            // Kategori 3: Hijau (Midfielder)
            "MIDFIELD", "DEFENSIVE_MIDFIELD", "CENTRAL_MIDFIELD", "ATTACKING_MIDFIELD", "WINGER" -> R.color.green_midfield

            // Kategori 4: Merah (Forward/Offence)
            "OFFENCE", "CENTRE_FORWARD", "LEFT_WINGER", "RIGHT_WINGER", "STRIKER", "ATTACKER" -> R.color.red_offence

            null, "" -> assignRandomColor()
            else -> assignRandomColor()
        }

        holder.playerCard.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, determinedColorResId))

        // 💡 PERUBAHAN 2: Tambahkan Click Listener
        holder.itemView.setOnClickListener {
            listener.onPlayerClicked(player)
        }
    }

    // Fungsi untuk memilih warna default secara random
    private fun assignRandomColor(): Int {
        val randomChoice = Random.nextInt(3)

        return when (randomChoice) {
            0 -> R.color.blue_defense
            1 -> R.color.green_midfield
            2 -> R.color.red_offence
            else -> R.color.white
        }
    }

    override fun getItemCount() = players.size
}