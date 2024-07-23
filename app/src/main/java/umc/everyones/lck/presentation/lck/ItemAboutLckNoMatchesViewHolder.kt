package umc.everyones.lck.presentation.lck

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemAboutLckNoMatchesBinding

class ItemAboutLckNoMatchesViewHolder(private val binding: ItemAboutLckNoMatchesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
    }

    companion object {
        fun from(parent: ViewGroup): ItemAboutLckNoMatchesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemAboutLckNoMatchesBinding.inflate(inflater, parent, false)
            return ItemAboutLckNoMatchesViewHolder(binding)
        }
    }
}
