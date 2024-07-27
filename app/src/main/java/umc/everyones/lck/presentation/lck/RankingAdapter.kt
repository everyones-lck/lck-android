package umc.everyones.lck.presentation.lck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R

class RankingAdapter(private val teams: List<RankingData>) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_about_lck_ranking, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val team = teams[position]
        holder.bind(team)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewTeamLogo: ImageView = itemView.findViewById(R.id.iv_about_lck_ranking)
        private val textViewTeamName: TextView = itemView.findViewById(R.id.tv_about_lck_ranking)

        fun bind(team: RankingData) {
            imageViewTeamLogo.setImageResource(team.logoResIdTop)
            textViewTeamName.text = team.teamNameTop
        }
    }
}
