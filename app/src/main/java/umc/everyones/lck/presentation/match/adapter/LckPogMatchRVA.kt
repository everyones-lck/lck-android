package umc.everyones.lck.presentation.match.adapter

import android.view.LayoutInflater
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
    private var setCount: Int,  // 세트 수를 받아서 탭을 동적으로 추가
    private val onTabSelected: (Int) -> Unit  // 탭 선택 시 호출할 함수
) : ListAdapter<CommonTodayMatchPogModel, LckPogMatchRVA.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLckPogMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun updateSetCount(newSetCount: Int) {
        setCount = newSetCount
        notifyDataSetChanged() // 세트 수 변경 시 UI 업데이트
    }

    inner class ViewHolder(private val binding: ItemLckPogMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val playerAdapter = LckPogPlayerRVA()

        init {
            binding.rvTodayMatchLckPogPlayer.adapter = playerAdapter

            // 탭 레이아웃 초기화 및 세트 수에 맞춰 탭 추가
            binding.tabTodayMatchLckPog.removeAllTabs()
            for (i in 1..setCount) {
                binding.tabTodayMatchLckPog.addTab(binding.tabTodayMatchLckPog.newTab().setText("${i.toOrdinal()} POG"))
            }
            binding.tabTodayMatchLckPog.addTab(binding.tabTodayMatchLckPog.newTab().setText("by Match"))

            // 탭의 마진 설정
            for (i in 0 until binding.tabTodayMatchLckPog.tabCount) {
                val tab = (binding.tabTodayMatchLckPog.getChildAt(0) as? ViewGroup)?.getChildAt(i)
                tab?.let {
                    val layoutParams = it.layoutParams as LinearLayout.LayoutParams
                    layoutParams.marginStart = 20
                    layoutParams.marginEnd = 20 // 20dp margin between tabs
                    it.layoutParams = layoutParams
                }
            }

            // 탭 선택 시 리스너 설정
            binding.tabTodayMatchLckPog.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let { position ->
                        onTabSelected(position)  // 선택된 탭의 인덱스를 전달
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }

        fun bind(item: CommonTodayMatchPogModel) {
            binding.tabTodayMatchLckPog.getTabAt(item.tabIndex)?.select()
            binding.tvTodayMatchLckPogMatchTitle.text = "${item.seasonInfo} ${item.matchNumber.toOrdinal()} Match"
            binding.tvTodayMatchLckPogMatchDate.text = item.matchDate
            playerAdapter.submitList(listOf(item))
        }
    }
    fun updatePlayers(players: List<CommonTodayMatchPogModel>) {
        submitList(players)
    }

    class DiffCallback : DiffUtil.ItemCallback<CommonTodayMatchPogModel>() {
        override fun areItemsTheSame(oldItem: CommonTodayMatchPogModel, newItem: CommonTodayMatchPogModel): Boolean {
            return oldItem.id == newItem.id // 각 항목의 고유 ID로 비교
        }

        override fun areContentsTheSame(oldItem: CommonTodayMatchPogModel, newItem: CommonTodayMatchPogModel): Boolean {
            return oldItem == newItem // 전체 항목이 동일한지 비교
        }
    }
}