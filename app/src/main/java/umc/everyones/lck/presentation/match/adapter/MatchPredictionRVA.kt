package umc.everyones.lck.presentation.match.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.databinding.ItemTodayMatchPredictionBinding
import umc.everyones.lck.domain.model.response.match.MatchTodayMatchModel
import umc.everyones.lck.domain.model.todayMatch.LckMatch
import umc.everyones.lck.util.extension.setOnSingleClickListener

class MatchPredictionRVA(private val onOptionSelected: (Int) -> Unit) :
    ListAdapter<MatchTodayMatchModel, MatchPredictionRVA.MatchPredictionViewHolder>(PredictionOptionDiffCallback()) {

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
            onOptionSelected(team)
        }
    }

    inner class MatchPredictionViewHolder(private val binding: ItemTodayMatchPredictionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(match: MatchTodayMatchModel, selectedTeam: Int?, onClick: (Int) -> Unit) {
            // 팀 로고 설정
            Glide.with(binding.root.context)
                .load(match.team1Logo)
                .into(binding.ivTodayMatchPredictionTeam1Logo)

            Glide.with(binding.root.context)
                .load(match.team2Logo)
                .into(binding.ivTodayMatchPredictionTeam2Logo)

            // 라디오 버튼 상태 설정
            binding.btnTodayMatchPredictionRadio1.isChecked = selectedTeam == match.team1Id
            binding.btnTodayMatchPredictionRadio2.isChecked = selectedTeam == match.team2Id

            // 클릭 이벤트 설정
            binding.layoutTodayMatchPredictionContainer1.setOnSingleClickListener {
                onClick(match.team1Id)
            }
            binding.layoutTodayMatchPredictionContainer2.setOnSingleClickListener {
                onClick(match.team2Id)
            }
        }
    }

    class PredictionOptionDiffCallback : DiffUtil.ItemCallback<MatchTodayMatchModel>() {
        override fun areItemsTheSame(oldItem: MatchTodayMatchModel, newItem: MatchTodayMatchModel): Boolean {
            return oldItem.team1Id == newItem.team1Id && oldItem.team2Id == newItem.team2Id
        }

        override fun areContentsTheSame(oldItem: MatchTodayMatchModel, newItem: MatchTodayMatchModel): Boolean {
            return oldItem == newItem
        }
    }
}