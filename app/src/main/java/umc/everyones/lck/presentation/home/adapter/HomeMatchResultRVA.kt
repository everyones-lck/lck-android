package umc.everyones.lck.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ItemHomeMatchResultBinding
import umc.everyones.lck.domain.model.response.home.HomeTodayMatchModel
import umc.everyones.lck.domain.model.todayMatch.LckMatch

class HomeMatchResultRVA(
    private val items: List<HomeTodayMatchModel.RecentMatchResultModel>
) : RecyclerView.Adapter<HomeMatchResultRVA.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeMatchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemHomeMatchResultBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeTodayMatchModel.RecentMatchResultModel) {
            Glide.with(binding.root.context)
                .load(item.team1LogoUrl)
                .into(binding.ivHomeResultLogo1)
            binding.tvHomeResultDate1.text = item.matchDate
            binding.tvHomeResultTeam1.text = item.team1Name
            binding.tvHomeResultTeam2.text = item.team2Name

            // 우승 팀 표시 로직
            when (item.matchResult) {
                "TEAM1_WIN" -> {
                    // 팀 1이 우승했을 경우, 팀 1 이름의 스타일을 TextAppearance.LCK.bold로 설정
                    binding.tvHomeResultTeam1.setTextAppearance(R.style.TextAppearance_LCK_Bold)
                }
                "TEAM2_WIN" -> {
                    // 팀 2가 우승했을 경우, 팀 2 이름의 스타일을 TextAppearance.LCK.bold로 설정
                    binding.tvHomeResultTeam2.setTextAppearance(R.style.TextAppearance_LCK_Bold)
                }
                else -> {
                    // 무승부 또는 기타 결과 처리 시 별도의 처리 불필요
                }
            }

        }
    }
}
