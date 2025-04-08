package umc.everyones.lck.presentation.lck.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R
import umc.everyones.lck.presentation.lck.adapter.HistoryAdapter.HistoryViewHolder
import umc.everyones.lck.presentation.lck.data.HistoryData
import umc.everyones.lck.presentation.lck.data.PlayerCareerData

class PlayerCareerAdapter() : RecyclerView.Adapter<PlayerCareerAdapter.PlayerCareerViewHolder>() {

    private var title: String = ""
    private var items: List<String> = emptyList()

    constructor(items: List<String>, title: String) : this() {
        setData(title, items)
    }

    fun setData(title: String, items: List<String>) {
        this.title = title
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerCareerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_about_lck_player, parent, false)
        return PlayerCareerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerCareerViewHolder, position: Int) {
        val detail = items[position]
        val parts = detail.split(" ", limit = 2)
        val year = parts.getOrNull(0) ?: ""
        val content = parts.getOrNull(1) ?: ""

        holder.yearTextView.text = year
        holder.contentTextView.text = content
    }

    override fun getItemCount(): Int = items.size

    inner class PlayerCareerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val yearTextView: TextView = view.findViewById(R.id.tv_about_lck_team_player_year)
        val contentTextView: TextView = view.findViewById(R.id.tv_about_lck_team_player_detail)
    }
}
