package umc.everyones.lck.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemHomeMatchResultBinding
import umc.everyones.lck.domain.model.todayMatch.LckMatch

class HomeMatchResultRVA(
    private val items: List<LckMatch>
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

        fun bind(item: LckMatch) {
            binding.ivHomeResultLogo1.setImageResource(item.team1LogoBlur)
            binding.tvHomeResultDate1.text = item.matchDate
            binding.tvHomeResultTeam1.text = item.team1Name  // 여기에 조건에 따라 우승 팀 이름을 표시할 수 있습니다.
            binding.tvHomeResultTeam2.text = item.team2Name
            // 우승 팀 로고를 표시할 로직이 필요하면 추가해야 합니다.
        }
    }
}
