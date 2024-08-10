package umc.everyones.lck.presentation.match.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemTodayPogPlayerBinding
import umc.everyones.lck.domain.model.todayMatch.TodayPog

class TodayPogPlayerRVA : ListAdapter<TodayPog, TodayPogPlayerRVA.PogPlayerViewHolder>(PogPlayerDiffCallback()) {

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
        }
    }

    inner class PogPlayerViewHolder(private val binding: ItemTodayPogPlayerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todayPog: TodayPog, isSelected: Boolean, onItemClick: () -> Unit) {
            binding.ivTodayPogPlayer.setImageResource(todayPog.playerImg)

            // 아이템이 선택되었는지 여부에 따라 색상 필터 적용
            if (isSelected) {
                binding.ivTodayPogPlayer.colorFilter = null
            } else {
                // 선택되지 않은 경우 흑백 처리
                val matrix = ColorMatrix().apply { setSaturation(0f) }
                binding.ivTodayPogPlayer.colorFilter = ColorMatrixColorFilter(matrix)
            }
            // 아이템 클릭 시 실행할 리스너 설정
            binding.root.setOnClickListener {
                onItemClick()
            }
        }
    }

    class PogPlayerDiffCallback : DiffUtil.ItemCallback<TodayPog>() {
        override fun areItemsTheSame(oldItem: TodayPog, newItem: TodayPog): Boolean {
            return oldItem.playerImg == newItem.playerImg
        }

        override fun areContentsTheSame(oldItem: TodayPog, newItem: TodayPog): Boolean {
            return oldItem == newItem
        }
    }
}