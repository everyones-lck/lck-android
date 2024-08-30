package umc.everyones.lck.presentation.lck.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.R
import umc.everyones.lck.data.dto.response.about_lck.LckPlayerDetailsResponseDto
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener
import umc.everyones.lck.presentation.lck.data.PlayerData

class PlayerAdapter(
    private val playerList: List<PlayerData>,
    private val listener: OnPlayerItemClickListener?
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName: TextView = itemView.findViewById(R.id.tv_about_lck_player_name)
        val playerTeamLogo: ImageView = itemView.findViewById(R.id.iv_about_lck_player_team_logo)
        val playerImage: ImageView = itemView.findViewById(R.id.iv_about_lck_player)
        val teamColorImage: ImageView = itemView.findViewById(R.id.iv_about_lck_player_team_color)
        val playerPosition: ImageView = itemView.findViewById(R.id.iv_about_lck_player_position)
        val playerLeaderPosition: ImageView = itemView.findViewById(R.id.iv_about_lck_player_leader_position)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && listener != null) {
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

        if (player.position == AboutLckPlayerDetailsModel.PlayerPosition.COACH) {
            holder.playerLeaderPosition.visibility = View.GONE

            if (player.isCaptain == true) {
                holder.playerPosition.setImageResource(R.drawable.ic_coach)
                holder.playerPosition.visibility = View.VISIBLE
            } else {
                holder.playerPosition.visibility = View.GONE
            }
        } else {
            holder.playerPosition.setImageResource(getPositionDrawable(player.position))
            holder.playerPosition.visibility = View.VISIBLE

            if (player.isCaptain == true) {
                holder.playerLeaderPosition.setImageResource(R.drawable.ic_coach)
                holder.playerLeaderPosition.visibility = View.VISIBLE
            } else {
                holder.playerLeaderPosition.visibility = View.GONE
            }
        }

        holder.playerImage.bringToFront()
        holder.teamColorImage.bringToFront()
        holder.playerTeamLogo.bringToFront()
        holder.playerName.bringToFront()
        holder.playerPosition.bringToFront()
        holder.playerLeaderPosition.bringToFront()
    }

    private fun getPositionDrawable(position: AboutLckPlayerDetailsModel.PlayerPosition?): Int {
        return when (position) {
            AboutLckPlayerDetailsModel.PlayerPosition.TOP -> R.drawable.ic_top
            AboutLckPlayerDetailsModel.PlayerPosition.JUNGLE -> R.drawable.ic_jgl
            AboutLckPlayerDetailsModel.PlayerPosition.MID -> R.drawable.ic_mid
            AboutLckPlayerDetailsModel.PlayerPosition.BOT -> R.drawable.ic_bot
            AboutLckPlayerDetailsModel.PlayerPosition.SUPPORT -> R.drawable.ic_support
            AboutLckPlayerDetailsModel.PlayerPosition.COACH -> R.drawable.ic_coach
            null -> R.drawable.ic_coach
        }
    }

    override fun getItemCount() = playerList.size
}
