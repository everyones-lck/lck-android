package umc.everyones.lck.presentation.match.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Comment
import umc.everyones.lck.databinding.ItemLckPogPlayerBinding
import umc.everyones.lck.domain.model.todayMatch.Player

class LckPogPlayerRVA() :
    ListAdapter<Player, LckPogPlayerRVA.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLckPogPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size


    inner class ViewHolder(private val binding: ItemLckPogPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Player) {
            binding.tvLckPogPlayerName.text = item.name
            binding.ivLckPogPlayerProfile.setImageResource(item.profileImageResId)
        }
    }
    class DiffCallback : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Player, newItem: Player) =
            oldItem == newItem
    }
}