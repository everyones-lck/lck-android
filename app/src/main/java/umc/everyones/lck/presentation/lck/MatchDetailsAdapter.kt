package umc.everyones.lck.presentation.lck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R

class MatchDetailsAdapter(private val matchDetails: List<MatchData>) :
    RecyclerView.Adapter<MatchDetailsAdapter.MatchDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_about_lck_matches_detail, parent, false)
        return MatchDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchDetailViewHolder, position: Int) {
        val detail = matchDetails[position]
        holder.bind(detail)
    }

    override fun getItemCount(): Int = matchDetails.size

    class MatchDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivTeam1: ImageView = itemView.findViewById(R.id.iv_about_lck_team1)
        private val ivTeam2: ImageView = itemView.findViewById(R.id.iv_about_lck_team2)
        private val tvMatchTitle: TextView = itemView.findViewById(R.id.tv_match_title)
        private val tvMatchTime: TextView = itemView.findViewById(R.id.tv_match_time)

        fun bind(detail: MatchData) {
            tvMatchTitle.text = detail.matchTitle
            tvMatchTime.text = detail.matchTime

            if (detail.imageResId1 != null) {
                ivTeam1.setImageResource(detail.imageResId1)
                ivTeam1.visibility = View.VISIBLE
            } else {
                ivTeam1.visibility = View.GONE
            }

            if (detail.imageResId2 != null) {
                ivTeam2.setImageResource(detail.imageResId2)
                ivTeam2.visibility = View.VISIBLE
            } else {
                ivTeam2.visibility = View.GONE
            }
        }
    }
}
