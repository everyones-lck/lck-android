package umc.everyones.lck.presentation.party.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemViewingPartyBinding
import umc.everyones.lck.domain.model.party.ViewingPartyItem

class ViewingPartyRVA(val readViewingParty: (Int) -> Unit) :
    ListAdapter<ViewingPartyItem, ViewingPartyRVA.ViewingPartyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewingPartyViewHolder {
        return ViewingPartyViewHolder(
            ItemViewingPartyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewingPartyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewingPartyViewHolder(private val binding: ItemViewingPartyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewingPartyItem: ViewingPartyItem) {
            with(binding){
                /*tvViewingPartyTitle.text = viewingPartyItem.title
                tvViewingPartyWriter.text = "${viewingPartyItem.writer} | ${viewingPartyItem.favoriteTeam}"
                tvViewingPartyAddress.text = viewingPartyItem.address
                tvViewingPartyDate.text = viewingPartyItem.date*/
                root.setOnClickListener {
                    readViewingParty(viewingPartyItem.id)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ViewingPartyItem>() {
        override fun areItemsTheSame(oldItem: ViewingPartyItem, newItem: ViewingPartyItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ViewingPartyItem, newItem: ViewingPartyItem) =
            oldItem == newItem
    }
}