package umc.everyones.lck.presentation.match.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemTodayMatchPredictionBinding
import umc.everyones.lck.domain.model.todayMatch.LckMatch

class MatchPredictionRVA(private val onOptionSelected: (LckMatch, Int) -> Unit) :
    ListAdapter<LckMatch, MatchPredictionRVA.MatchPredictionViewHolder>(PredictionOptionDiffCallback()) {

    private var selectedTeam: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchPredictionViewHolder {
        val binding = ItemTodayMatchPredictionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchPredictionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchPredictionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, selectedTeam) { team ->
            selectedTeam = team
            notifyDataSetChanged() // 선택된 팀이 변경되었음을 알림
            onOptionSelected(item, team)
        }
    }

    inner class MatchPredictionViewHolder(private val binding: ItemTodayMatchPredictionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(match: LckMatch, selectedTeam: Int?, onClick: (Int) -> Unit) {
            // 팀 로고 설정
            binding.ivTodayMatchPredictionTeam1Logo.setImageResource(match.team1LogoResId)
            binding.ivTodayMatchPredictionTeam2Logo.setImageResource(match.team2LogoResId)

            // 라디오 버튼 상태 설정
            binding.btnTodayMatchPredictionRadio1.isChecked = selectedTeam == 1
            binding.btnTodayMatchPredictionRadio2.isChecked = selectedTeam == 2

            // 클릭 이벤트 설정
            binding.layoutTodayMatchPredictionContainer1.setOnClickListener {
                onClick(1)
            }
            binding.layoutTodayMatchPredictionContainer2.setOnClickListener {
                onClick(2)
            }
        }
    }

    class PredictionOptionDiffCallback : DiffUtil.ItemCallback<LckMatch>() {
        override fun areItemsTheSame(oldItem: LckMatch, newItem: LckMatch): Boolean {
            return oldItem.team1LogoResId == newItem.team1LogoResId && oldItem.team2LogoResId == newItem.team2LogoResId
        }

        override fun areContentsTheSame(oldItem: LckMatch, newItem: LckMatch): Boolean {
            return oldItem == newItem
        }
    }
}