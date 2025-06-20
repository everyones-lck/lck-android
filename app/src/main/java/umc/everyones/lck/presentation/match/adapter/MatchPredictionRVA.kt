package umc.everyones.lck.presentation.match.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.R
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
            // 이름 설정
            binding.tvTodayMatchPredictionTeam1Name.text = match.team1Name
            binding.tvTodayMatchPredictionTeam2Name.text = match.team2Name

            val team1Color = teamColorMap[match.team1Name] ?: R.color.gray_300
            val team2Color = teamColorMap[match.team2Name] ?: R.color.gray_300

            val team1Drawable = binding.ivTodayMatchPredictionTeam1Box.drawable?.mutate()
            val team2Drawable = binding.ivTodayMatchPredictionTeam2Box.drawable?.mutate()

            team1Drawable?.setTint(
                if (selectedTeam == match.team1Id) binding.root.context.getColor(team1Color)
                else binding.root.context.getColor(R.color.gray_700)
            )
            team2Drawable?.setTint(
                if (selectedTeam == match.team2Id) binding.root.context.getColor(team2Color)
                else binding.root.context.getColor(R.color.gray_700)
            )

            // 클릭 이벤트 설정
            binding.ivTodayMatchPredictionTeam1Box.setOnSingleClickListener {
                onClick(match.team1Id)
            }
            binding.ivTodayMatchPredictionTeam2Box.setOnSingleClickListener {
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