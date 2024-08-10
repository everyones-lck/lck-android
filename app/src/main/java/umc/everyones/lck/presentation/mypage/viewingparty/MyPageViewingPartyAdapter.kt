package umc.everyones.lck.presentation.mypage.viewingparty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.data.models.ViewingPartyItem
import umc.everyones.lck.databinding.ItemMypageViewingPartyBinding
import java.util.Date

class MyPageViewingPartyAdapter(
    private var items: List<ViewingPartyItem>,
    private val isHost: Boolean
) : RecyclerView.Adapter<MyPageViewingPartyAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMypageViewingPartyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ViewingPartyItem) {
            val isPastParty = item.date.before(Date())

            binding.item = item
            binding.isHost = isHost
            binding.isPastParty = isPastParty
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMypageViewingPartyBinding.inflate(inflater, parent, false)
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
