package umc.everyones.lck.presentation.match.adapter

import android.content.res.ColorStateList
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ItemTodayPogPlayerBinding
import umc.everyones.lck.domain.model.response.match.PogPlayerTodayMatchModel
import umc.everyones.lck.domain.model.todayMatch.TodayPog
import umc.everyones.lck.util.extension.setOnSingleClickListener

class TodayPogPlayerRVA(
    private val onPlayerSelected: (Int) -> Unit // playerId를 전달하는 람다 함수
) : ListAdapter<PogPlayerTodayMatchModel.InformationModel, TodayPogPlayerRVA.PogPlayerViewHolder>(PogPlayerDiffCallback()) {

    private var selectedPosition: Int? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PogPlayerViewHolder {
        val binding = ItemTodayPogPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PogPlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PogPlayerViewHolder, position: Int) {
        holder.bind(getItem(position), position == selectedPosition) {
            // 이전에 선택된 아이템의 포지션을 저장
            val previousSelectedPosition = selectedPosition
            // 현재 선택된 포지션 업데이트
            selectedPosition = if (selectedPosition == position) null else position
            previousSelectedPosition?.let { notifyItemChanged(it) }
            notifyItemChanged(position)

            // 선택된 playerId를 전달
            onPlayerSelected(getItem(position).playerId)
        }
    }

    inner class PogPlayerViewHolder(private val binding: ItemTodayPogPlayerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(player: PogPlayerTodayMatchModel.InformationModel, isSelected: Boolean, onItemClick: () -> Unit) {

//            // 아이템이 선택되었는지 여부에 따라 색상 필터 적용
//            if (isSelected) {
//                binding.ivTodayPogPlayer.colorFilter = null
//            } else {
//                // 선택되지 않은 경우 흑백 처리
//                val matrix = ColorMatrix().apply { setSaturation(0f) }
//                binding.ivTodayPogPlayer.colorFilter = ColorMatrixColorFilter(matrix)
//            }
            val context = binding.root.context

//            // 팀 컬러 설정
//            val teamColorRes = teamColorMap[player.teamName] ?: R.color.gray_700
//            binding.ivTodayPogPlayerTeamColor.imageTintList =
//                ColorStateList.valueOf(ContextCompat.getColor(context, teamColorRes))


            // 플레이어 이름 설정
            binding.tvTodayPogPlayerName.text = player.playerName

            // 선택 여부에 따른 색상 적용
            if (isSelected) {
                binding.tvTodayPogPlayerName.setTextColor(ContextCompat.getColor(context, R.color.white))
                binding.tvTodayPogPlayerName.setBackgroundResource(R.drawable.shape_rect_4_grayscale_100_line) // 선택된 배경
            } else {
                binding.tvTodayPogPlayerName.setTextColor(ContextCompat.getColor(context, R.color.gray_800))
                binding.tvTodayPogPlayerName.setBackgroundResource(R.drawable.shape_rect_4_gray_800_line) // 기본 배경
            }

            // 아이템 클릭 시 실행할 리스너 설정
            binding.root.setOnSingleClickListener {
                onItemClick()
            }
        }
    }

    class PogPlayerDiffCallback : DiffUtil.ItemCallback<PogPlayerTodayMatchModel.InformationModel>() {
        override fun areItemsTheSame(oldItem: PogPlayerTodayMatchModel.InformationModel, newItem: PogPlayerTodayMatchModel.InformationModel): Boolean {
            return oldItem.playerId == newItem.playerId
        }

        override fun areContentsTheSame(oldItem: PogPlayerTodayMatchModel.InformationModel, newItem: PogPlayerTodayMatchModel.InformationModel): Boolean {
            return oldItem == newItem
        }
    }

    private val teamColorMap = mapOf(
        "Gen.G" to R.color.gen_g,
        "GEN" to R.color.gen_g,
        "HLE" to R.color.hanhwa,
        "DK" to R.color.dplus_kia,
        "T1" to R.color.t1,
        "KT" to R.color.kt_rolster,
        "KDF" to R.color.kwangdong_freecs,
        "DNF" to R.color.dnf,
        "BNK" to R.color.bnk,
        "BFX" to R.color.bnk,
        "NS" to R.color.ns,
        "DRX" to R.color.drx,
        "BRO" to R.color.ok_brion
        // 추가적인 팀과 색상 매핑
    )
}