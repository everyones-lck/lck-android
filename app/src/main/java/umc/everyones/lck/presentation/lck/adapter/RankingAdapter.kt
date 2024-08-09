package umc.everyones.lck.presentation.lck.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R
import umc.everyones.lck.presentation.lck.util.OnTeamClickListener
import umc.everyones.lck.presentation.lck.data.RankingData

class RankingAdapter(
    private val teams: List<RankingData>,
    private val listener: OnTeamClickListener
) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_about_lck_ranking, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val team = teams[position]
        holder.bind(team, listener)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    inner class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewTeamLogo: ImageView = itemView.findViewById(R.id.iv_about_lck_ranking)
        private val textViewTeamName: TextView = itemView.findViewById(R.id.tv_about_lck_ranking)
        private val rankingTv: TextView = itemView.findViewById(R.id.tv_about_lck_ranking)

        fun bind(team: RankingData, listener: OnTeamClickListener) {
            imageViewTeamLogo.setImageResource(team.logoResIdTop)
            textViewTeamName.text = team.teamNameTop
            rankingTv.text = team.ranking.toString()

            imageViewTeamLogo.setOnClickListener {
                listener.onTeamClick(team)
            }

            textViewTeamName.setOnClickListener {
                listener.onTeamClick(team)
            }
        }
    }
}

