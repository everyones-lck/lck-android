package umc.everyones.lck.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
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
            binding.tvHomeResultDate1.text = item.matchDate
            binding.tvHomeResultTeam1.text = item.team1Name
            binding.tvHomeResultTeam2.text = item.team2Name

            // 우승 팀 표시 로직
            when (item.matchResult) {
                "TEAM1_WIN" -> {
                    binding.tvHomeResultTeam1.text = item.team1Name
                    binding.tvHomeResultTeam1.setTextAppearance(R.style.TextAppearance_LCK_Bold)
                    binding.tvHomeResultTeam2.text = item.team2Name

                    val colorResId = teamColorMap[item.team1Name] ?: R.color.gray
                    val dotDrawable = binding.ivHomeWinnerDot.drawable?.mutate()
                    dotDrawable?.setTint(binding.root.context.getColor(colorResId))
                    binding.ivHomeWinnerDot.setImageDrawable(dotDrawable)
                }
                "TEAM2_WIN" -> {
                    binding.tvHomeResultTeam1.text = item.team2Name
                    binding.tvHomeResultTeam1.setTextAppearance(R.style.TextAppearance_LCK_Bold)
                    binding.tvHomeResultTeam2.text = item.team1Name

                    val colorResId = teamColorMap[item.team2Name] ?: R.color.gray
                    val dotDrawable = binding.ivHomeWinnerDot.drawable?.mutate()
                    dotDrawable?.setTint(binding.root.context.getColor(colorResId))
                    binding.ivHomeWinnerDot.setImageDrawable(dotDrawable)
                }
                else -> {
                    // 무승부 또는 기타 결과 처리 시 별도의 처리 불필요
                }
            }

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
