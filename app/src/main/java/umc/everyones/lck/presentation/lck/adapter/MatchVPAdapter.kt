package umc.everyones.lck.presentation.lck.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ItemAboutLckMatchesBinding
import umc.everyones.lck.databinding.ItemAboutLckMatchesDetailBinding
import umc.everyones.lck.databinding.ItemLckMatchContentBinding
import umc.everyones.lck.domain.model.about_lck.AboutLckMatchDetailsModel
import umc.everyones.lck.domain.model.party.ViewingPartyItem
import umc.everyones.lck.presentation.lck.AboutLCKFragment
import umc.everyones.lck.presentation.lck.data.MatchData


class MatchVPAdapter : ListAdapter<AboutLCKFragment.Test, MatchVPAdapter.MatchViewHolder>(DiffCallback()) {

    private val matchDetailsList: MutableList<List<MatchData>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(
            ItemAboutLckMatchesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    fun addMatchDetails(details: List<MatchData>) {
        matchDetailsList.add(details)
        notifyDataSetChanged()
    }

    fun getMatchDataList(): List<MatchData> {
        return matchDetailsList.flatten() // 모든 데이터를 리스트로 반환
    }

    fun clearMatchDetails() {
        matchDetailsList.clear()
        notifyDataSetChanged()  // 데이터가 변경되었음을 어댑터에 알림
    }

    inner class MatchViewHolder(private val binding: ItemAboutLckMatchesBinding) : RecyclerView.ViewHolder(binding.root) {
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.rv_about_lck_match_details)

        fun bind(matchDetails: AboutLCKFragment.Test) {
            binding.rvAboutLckMatchDetails.layoutManager = LinearLayoutManager(itemView.context)
            val adapter = MatchDetailsAdapter()
            binding.rvAboutLckMatchDetails.adapter = adapter
            adapter.submitList(matchDetails.list)
            binding.rvAboutLckMatchDetails.isNestedScrollingEnabled = false // 스크롤 비활성화
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<AboutLCKFragment.Test>() {
        override fun areItemsTheSame(oldItem: AboutLCKFragment.Test, newItem: AboutLCKFragment.Test) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: AboutLCKFragment.Test, newItem: AboutLCKFragment.Test) =
            oldItem == newItem
    }
}
