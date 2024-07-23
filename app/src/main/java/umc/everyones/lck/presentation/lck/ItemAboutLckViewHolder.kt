import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R
import umc.everyones.lck.presentation.lck.MatchData

class ItemAboutLckViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ivTeam1: ImageView = itemView.findViewById(R.id.iv_about_lck_team1)
    private val ivTeam2: ImageView = itemView.findViewById(R.id.iv_about_lck_team2)
    private val tvMatchTitle: TextView = itemView.findViewById(R.id.tv_match_title)
    private val tvMatchTime: TextView = itemView.findViewById(R.id.tv_match_time)
    private val ivTeam3: ImageView = itemView.findViewById(R.id.iv_about_lck_team3)
    private val ivTeam4: ImageView = itemView.findViewById(R.id.iv_about_lck_team4)
    private val tvMatchTitle2: TextView = itemView.findViewById(R.id.tv_match_title2)
    private val tvMatchTime2: TextView = itemView.findViewById(R.id.tv_match_time2)

    fun bind(matchData: MatchData) {
        ivTeam1.setImageResource(matchData.imageResId1)
        ivTeam2.setImageResource(matchData.imageResId2)
        tvMatchTitle.text = matchData.matchTitle
        tvMatchTime.text = matchData.matchTime
        ivTeam3.setImageResource(matchData.imageResId3)
        ivTeam4.setImageResource(matchData.imageResId4)
        tvMatchTitle2.text = matchData.matchTitle2
        tvMatchTime2.text = matchData.matchTime2
    }

    companion object {
        fun from(parent: ViewGroup): ItemAboutLckViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_about_lck, parent, false)
            return ItemAboutLckViewHolder(view)
        }
    }
}