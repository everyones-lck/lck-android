package umc.everyones.lck.presentation.match.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import umc.everyones.lck.databinding.ItemLckPogMatchBinding
import umc.everyones.lck.domain.model.todayMatch.LckPog

class LckPogMatchRVA(private val items: List<LckPog>) :
    RecyclerView.Adapter<LckPogMatchRVA.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLckPogMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemLckPogMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LckPog) {
            binding.tvTodayMatchLckPogMatchTitle.text = item.matchTitle
            binding.tvTodayMatchLckPogMatchDate.text = item.matchDate

            val playerAdapter = LckPogPlayerRVA()
            binding.rvTodayMatchLckPogPlayer.adapter = playerAdapter

            binding.tabTodayMatchLckPog.addTab(binding.tabTodayMatchLckPog.newTab().setText("1st POG"))
            binding.tabTodayMatchLckPog.addTab(binding.tabTodayMatchLckPog.newTab().setText("2nd POG"))
            binding.tabTodayMatchLckPog.addTab(binding.tabTodayMatchLckPog.newTab().setText("3rd POG"))
            binding.tabTodayMatchLckPog.addTab(binding.tabTodayMatchLckPog.newTab().setText("by Match"))

            playerAdapter.submitList(item.players)
            // Set tab margins
            for (i in 0 until binding.tabTodayMatchLckPog.tabCount) {
                val tab = (binding.tabTodayMatchLckPog.getChildAt(0) as? ViewGroup)?.getChildAt(i)
                tab?.let {
                    val layoutParams = it.layoutParams as LinearLayout.LayoutParams
                    layoutParams.marginStart = 20
                    layoutParams.marginEnd = 20 // 10dp margin between tabs
                    it.layoutParams = layoutParams
                }
            }

            binding.tabTodayMatchLckPog.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val tabPosition = tab?.position ?: 0
                    // Update player list based on tab selection. Here just a simple example
                    playerAdapter.submitList(item.players) // you may want to update with different list
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }
}