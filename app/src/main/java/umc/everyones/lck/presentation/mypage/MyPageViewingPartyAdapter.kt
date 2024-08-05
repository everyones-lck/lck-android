package umc.everyones.lck.presentation.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.data.models.ViewingPartyItem
import umc.everyones.lck.databinding.ItemMypageViewingPartyBinding

class MyPageViewingPartyAdapter(private var items: List<ViewingPartyItem>) :
    RecyclerView.Adapter<MyPageViewingPartyAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMypageViewingPartyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ViewingPartyItem) {
            binding.tvMypageCommunityTitle.text = item.title
            binding.tvMypageCommunityDate.text = item.date.toString() // 날짜 포맷을 추가할 수도 있습니다
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMypageViewingPartyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ViewingPartyItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
