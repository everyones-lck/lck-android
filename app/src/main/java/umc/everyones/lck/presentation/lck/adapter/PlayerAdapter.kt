package umc.everyones.lck.presentation.lck.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import timber.log.Timber
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ItemAboutLckTeamBinding
import umc.everyones.lck.presentation.lck.data.PlayerData
import umc.everyones.lck.presentation.lck.util.OnPlayerItemClickListener

class PlayerAdapter(
    private val playerList: List<PlayerData>,
    private val listener: OnPlayerItemClickListener?
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(private val binding: ItemAboutLckTeamBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: PlayerData) {
            // 이름 세팅
            binding.tvAboutLckTeamName.text = player.name

            // 포지션 이미지 세팅
            val positionIcon = when (player.position?.name) {
                "TOP" -> R.drawable.ic_top
                "JUNGLE" -> R.drawable.ic_jgl
                "MID" -> R.drawable.ic_mid
                "BOT" -> R.drawable.ic_bot
                "SUPPORT" -> R.drawable.ic_support
                "COACH" -> R.drawable.ic_coach
                else -> R.drawable.ic_top
            }
            binding.ivAboutLckTeamPlayerPosition.setImageResource(positionIcon)

            // 캡틴 여부에 따른 텍스트 및 백그라운드 처리
            if (player.isCaptain == true) {
                binding.tvAboutLckTeamIsCaptain.text = "Leader"
            } else {
                binding.tvAboutLckTeamIsCaptain.text = "Member"
            }
            // 클릭 이벤트 처리
            binding.root.setOnClickListener {
                listener?.onPlayerItemClick(player)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemAboutLckTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        Timber.d("Binding player: ${playerList[position].name}")
        holder.bind(playerList[position])
    }

    override fun getItemCount(): Int = playerList.size
}
