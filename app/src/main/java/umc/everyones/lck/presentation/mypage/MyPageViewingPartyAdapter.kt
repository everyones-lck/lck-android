package umc.everyones.lck.presentation.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.data.models.ViewingPartyItem
import umc.everyones.lck.databinding.ItemMypageViewingPartyBinding

class MyPageViewingPartyAdapter(
    private var items: List<ViewingPartyItem>
) : RecyclerView.Adapter<MyPageViewingPartyAdapter.ViewHolder>() {

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

    inner class ViewHolder(private val binding: ItemMypageViewingPartyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ViewingPartyItem) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}