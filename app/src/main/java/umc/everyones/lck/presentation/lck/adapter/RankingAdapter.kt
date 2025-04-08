package umc.everyones.lck.presentation.lck.adapter

import android.graphics.Color
import android.graphics.drawable.VectorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.bumptech.glide.Glide
import umc.everyones.lck.R
import umc.everyones.lck.presentation.lck.util.OnTeamClickListener
import umc.everyones.lck.presentation.lck.data.RankingData

class RankingAdapter(
    private var teams: MutableList<RankingData>,
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

    fun updateTeams(newTeams: List<RankingData>) {
        teams.clear()
        teams.addAll(newTeams)
        notifyDataSetChanged()
    }

    private fun getTeamColor(teamId: Int): Int {
        return when (teamId) {
            2 -> Color.parseColor("#AA8B30")     // Gen.G
            3 -> Color.parseColor("#F3741B")     // hanwha
            4 -> Color.parseColor("#FFFFFF")     // dk
            5 -> Color.parseColor("#E91B3B")     // t1_kt_kdf_ns
            6 -> Color.parseColor("#E91B3B")     // t1_kt_kdf_ns
            7 -> Color.parseColor("#E91B3B")     // t1_kt_kdf_ns
            8 -> Color.parseColor("#F8E52F")     // bnk
            9 -> Color.parseColor("#E91B3B")     // ns
            10 -> Color.parseColor("#0017E7")     // drx
            11 -> Color.parseColor("#003202")     // ok
            else -> Color.parseColor("#E91B3B")
        }
    }

    private fun applyTeamColorToVectorBackground(imageView: ImageView, color: Int) {
        val drawable = imageView.drawable

        if (drawable is VectorDrawable) {
            drawable.mutate().setTint(color)
            imageView.setImageDrawable(drawable)
        } else {
            val compatDrawable = imageView.drawable as? VectorDrawableCompat
            compatDrawable?.mutate()?.setTint(color)
            imageView.setImageDrawable(compatDrawable)
        }
    }

    inner class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTeamName: TextView = itemView.findViewById(R.id.tv_about_lck_team_name)
        private val teamColorIv:ImageView = itemView.findViewById(R.id.iv_about_lck_ranking)
        private val rankingTv: TextView = itemView.findViewById(R.id.tv_about_lck_ranking)

        fun bind(team: RankingData, listener: OnTeamClickListener) {

            textViewTeamName.text = team.teamName
            rankingTv.text = team.ranking.toString()

            applyTeamColorToVectorBackground(teamColorIv, getTeamColor(team.teamId))

            itemView.setOnClickListener {
                listener.onTeamClick(team)
            }
        }
    }
}

