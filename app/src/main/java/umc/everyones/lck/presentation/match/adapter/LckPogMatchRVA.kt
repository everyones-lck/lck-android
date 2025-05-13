package umc.everyones.lck.presentation.match.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import umc.everyones.lck.databinding.ItemLckPogMatchBinding
import umc.everyones.lck.domain.model.response.match.CommonTodayMatchPogModel
import umc.everyones.lck.domain.model.todayMatch.LckPog
import umc.everyones.lck.util.extension.toOrdinal

class LckPogMatchRVA(

) : ListAdapter<CommonTodayMatchPogModel, LckPogMatchRVA.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLckPogMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemLckPogMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommonTodayMatchPogModel) {
            binding.tvTodayMatchLckPogMatchTitle.text = "${item.seasonInfo} ${item.matchNumber.toOrdinal()} Match"
            binding.tvTodayMatchLckPogMatchDate.text = item.matchDate

            val pog1st = item.setPogResponses.find { it.setIndex == 1 }
            val pog2nd = item.setPogResponses.find { it.setIndex == 2 }
            val pog3rd = item.setPogResponses.find { it.setIndex == 3 }
            val pog4th = item.setPogResponses.find { it.setIndex == 4 }
            val pog5th = item.setPogResponses.find { it.setIndex == 5 }
            val matchPog = item.matchPogResponse

            // 예: 1st POG 이름 텍스트 뷰에 세팅
            binding.tvTodayMatchLckPog1stPlayer.text = pog1st?.name ?: "-"
            binding.tvTodayMatchLckPog2ndPlayer.text = pog2nd?.name ?: "-"
            binding.tvTodayMatchLckPog3rdPlayer.text = pog3rd?.name ?: "-"
            binding.tvTodayMatchLckPog4thPlayer.text = pog4th?.name ?: "-"
            binding.tvTodayMatchLckPog5thPlayer.text = pog5th?.name ?: "-"
            binding.tvTodayMatchLckPogMatchPlayer.text = matchPog?.name ?: "-"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CommonTodayMatchPogModel>() {
        override fun areItemsTheSame(oldItem: CommonTodayMatchPogModel, newItem: CommonTodayMatchPogModel): Boolean {
            return oldItem.matchNumber == newItem.matchNumber // 각 항목의 고유 ID로 비교
        }

        override fun areContentsTheSame(oldItem: CommonTodayMatchPogModel, newItem: CommonTodayMatchPogModel): Boolean {
            return oldItem == newItem // 전체 항목이 동일한지 비교
        }
    }
}