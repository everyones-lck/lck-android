package umc.everyones.lck.presentation.lck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R

class PlayerCareerDetailAdapter(private val details: List<String>) :
    RecyclerView.Adapter<PlayerCareerDetailAdapter.PlayerDetailsViewHolder>() {

    inner class PlayerDetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val detailTextView: TextView = view.findViewById(R.id.tv_about_lck_player_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerDetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_about_lck_player_detail, parent, false)
        return PlayerDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerDetailsViewHolder, position: Int) {
        holder.detailTextView.text = details[position]
    }

    override fun getItemCount(): Int {
        return details.size
    }
}