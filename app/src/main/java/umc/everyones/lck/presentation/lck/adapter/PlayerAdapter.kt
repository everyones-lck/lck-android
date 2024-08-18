package umc.everyones.lck.presentation.lck.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.R
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener
import umc.everyones.lck.presentation.lck.data.PlayerData

class PlayerAdapter(
    private val playerList: List<PlayerData>,
    private val listener: OnPlayerItemClickListener
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName: TextView = itemView.findViewById(R.id.tv_about_lck_player_name)
        val playerTeamLogo: ImageView = itemView.findViewById(R.id.iv_about_lck_player_team_logo)
        val playerImage: ImageView = itemView.findViewById(R.id.iv_about_lck_player)
        val teamColorImage: ImageView = itemView.findViewById(R.id.iv_about_lck_player_team_color)
        val playerPosition: ImageView = itemView.findViewById(R.id.iv_about_lck_player_position)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onPlayerItemClick(playerList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_about_lck_member, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = playerList[position]
        holder.playerName.text = player.name

        Glide.with(holder.itemView.context)
            .load(player.playerImg)
            .into(holder.playerImage)

        holder.teamColorImage.setImageResource(player.teamColor)
        holder.playerTeamLogo.setImageResource(player.teamLogo)
        holder.playerPosition.setImageResource(player.position)

        holder.playerImage.bringToFront()
        holder.teamColorImage.bringToFront()
        holder.playerTeamLogo.bringToFront()
        holder.playerName.bringToFront()
        holder.playerPosition.bringToFront()
    }

    override fun getItemCount() = playerList.size
}
