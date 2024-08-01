package umc.everyones.lck.presentation.lck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R

class PlayerCareerAdapter(private val items: List<PlayerCareerData>) :
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

        holder.itemView.setOnClickListener {
            item.isExpanded = !item.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
