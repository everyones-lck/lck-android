package umc.everyones.lck.presentation.lck.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R
import umc.everyones.lck.presentation.lck.data.HistoryData
import umc.everyones.lck.presentation.lck.data.PlayerCareerData

class PlayerCareerAdapter(private val items: MutableList<PlayerCareerData>) :
    RecyclerView.Adapter<PlayerCareerAdapter.PlayerCareerViewHolder>() {

    inner class PlayerCareerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.tv_about_lck_winning_career)
        val expandIcon: ImageView = view.findViewById(R.id.iv_about_lck_team_player_down1)
        val detailsRecyclerView: RecyclerView = view.findViewById(R.id.rv_about_lck_player_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerCareerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_about_lck_player, parent, false)
        return PlayerCareerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerCareerViewHolder, position: Int) {
        val item = items[position]

        holder.titleTextView.text = item.title
        holder.detailsRecyclerView.visibility = if (item.isExpanded) View.VISIBLE else View.GONE
        holder.expandIcon.setImageResource(if (item.isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)

        // 세부 항목 어댑터 설정
        holder.detailsRecyclerView.layoutManager = LinearLayoutManager(holder.detailsRecyclerView.context)
        holder.detailsRecyclerView.adapter = PlayerCareerDetailAdapter(item.details)
        holder.detailsRecyclerView.setHasFixedSize(true)
        holder.detailsRecyclerView.isNestedScrollingEnabled = true

        holder.itemView.setOnClickListener {
            item.isExpanded = !item.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItems(): List<PlayerCareerData> = items

    fun updateItems(newItems: List<PlayerCareerData>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
