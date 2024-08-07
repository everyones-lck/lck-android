package umc.everyones.lck.presentation.match.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ItemLckMatchContentBinding
import umc.everyones.lck.domain.model.todayMatch.LckMatch

class LckMatchContentRVA(private val items: List<LckMatch>) :
    RecyclerView.Adapter<LckMatchContentRVA.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLckMatchContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemLckMatchContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LckMatch) {
            binding.tvTodayMatchLckMatch1Content.text = item.matchTitle
            binding.tvTodayMatchLckMatch1Date.text = item.matchDate
            binding.tvTodayMatchLckMatchTeam1Name.text = item.team1Name
            binding.tvTodayMatchLckMatchTeam2Name.text = item.team2Name
            binding.ivTodayMatchLckMatchTeam1Logo.setImageResource(item.team1LogoResId)
            binding.ivTodayMatchLckMatchTeam2Logo.setImageResource(item.team2LogoResId)
            binding.tvTodayMatchTeam1Percent.text = item.team1WinRate
            binding.tvTodayMatchTeam2Percent.text = item.team2WinRate

            val context = binding.root.context
            val team1Color = ContextCompat.getColor(context, teamColorMap[item.team1Name] ?: R.color.white)
            val team2Color = ContextCompat.getColor(context, teamColorMap[item.team2Name] ?: R.color.black)

            binding.tvTodayMatchTeam1Percent.setTextColor(team1Color)
            binding.tvTodayMatchTeam2Percent.setTextColor(team2Color)
            binding.tvTodayMatchTeam1Bar.backgroundTintList = ColorStateList.valueOf(team1Color)
            binding.tvTodayMatchTeam2Bar.backgroundTintList = ColorStateList.valueOf(team2Color)

            binding.tvTodayMatch1Prediction.setOnClickListener {
                // Navigate to TodayMatchPredictionFragment
                it.findNavController().navigate(R.id.todayMatchPredictionFragment)
            }

            binding.tvTodayMatch1Pog.setOnClickListener {
                // Navigate to TodayMatchTodayPogFragment
                it.findNavController().navigate(R.id.todayMatchTodayPogFragment)
            }
            // Set the weight of the bars based on winrate
            val team1WinRate = item.team1WinRate.replace("%", "").toFloat() / 100
            val team2WinRate = item.team2WinRate.replace("%", "").toFloat() / 100

            val params1 = binding.tvTodayMatchTeam1Bar.layoutParams as LinearLayout.LayoutParams
            params1.weight = team1WinRate
            binding.tvTodayMatchTeam1Bar.layoutParams = params1

            val params2 = binding.tvTodayMatchTeam2Bar.layoutParams as LinearLayout.LayoutParams
            params2.weight = team2WinRate
            binding.tvTodayMatchTeam2Bar.layoutParams = params2
        }
    }
    private val teamColorMap = mapOf(
        "Gen.G" to R.color.gen_g,
        "T1" to R.color.t1,
        "DK" to R.color.dplus_kia,
        "DRX" to R.color.drx
        // 추가적인 팀과 색상 매핑
    )
}