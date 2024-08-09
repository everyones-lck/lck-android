package umc.everyones.lck.presentation.home.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ItemHomeMatchContentBinding
import umc.everyones.lck.domain.model.todayMatch.LckMatch

class HomeMatchContentVPA(private val items: List<LckMatch>, private val onClick: () -> Unit):
    RecyclerView.Adapter<HomeMatchContentVPA.HomeMatchContentViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeMatchContentVPA.HomeMatchContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeMatchContentBinding.inflate(inflater, parent, false)
        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return HomeMatchContentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeMatchContentVPA.HomeMatchContentViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class HomeMatchContentViewHolder(private val binding: ItemHomeMatchContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LckMatch) {
            binding.tvHomeTodayMatchContent.text = item.matchTitle
            binding.tvHomeTodayMatchDate.text = item.matchDate
            binding.ivHomeTodayMatchLogo1.setImageResource(item.team1LogoResId)
            binding.ivHomeTodayMatchLogo2.setImageResource(item.team2LogoResId)
            binding.tvHomeTodayMatchTeam1.text = item.team1Name
            binding.tvHomeTodayMatchTeam2.text = item.team2Name

            val context = binding.root.context
            val team1Color = ContextCompat.getColor(context, teamColorMap[item.team1Name] ?: R.color.white)
            val team2Color = ContextCompat.getColor(context, teamColorMap[item.team2Name] ?: R.color.black)
            binding.ivHomeTodayMatchBar1.backgroundTintList = ColorStateList.valueOf(team1Color)
            binding.ivHomeTodayMatchBar2.backgroundTintList = ColorStateList.valueOf(team2Color)

            itemView.setOnClickListener {
<<<<<<< Updated upstream
                it.findNavController().navigate(R.id.todayMatchTab)
=======
                onClick()
>>>>>>> Stashed changes
            }
        }
    }
    private val teamColorMap = mapOf(
        "Gen.G" to R.color.gen_g,
        "T1" to R.color.t1,
        "DK" to R.color.gray_indicator,
        "DRX" to R.color.drx
        // 추가적인 팀과 색상 매핑
    )
}