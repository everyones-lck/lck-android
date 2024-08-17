package umc.everyones.lck.presentation.party.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.databinding.ItemViewingPartyBinding
import umc.everyones.lck.domain.model.party.ViewingPartyItem
import umc.everyones.lck.domain.model.response.party.ViewingPartyListModel

class ViewingPartyRVA(val readViewingParty: (Long) -> Unit) :
    PagingDataAdapter<ViewingPartyListModel.ViewingPartyElementModel, ViewingPartyRVA.ViewingPartyViewHolder>(DiffCallback()) {

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
        val viewingParty = getItem(position)
        if(viewingParty != null) {
            holder.bind(viewingParty)
        }
    }

    inner class ViewingPartyViewHolder(private val binding: ItemViewingPartyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewingPartyItem: ViewingPartyListModel.ViewingPartyElementModel) {
            with(binding){
                tvViewingPartyTitle.text = viewingPartyItem.name
                tvViewingPartyWriter.text = viewingPartyItem.writerInfo
                tvViewingPartyAddress.text = viewingPartyItem.location
                tvViewingPartyDate.text = viewingPartyItem.partyDate
                Glide.with(ivViewingPartyProfile.context)
                    .load(viewingPartyItem.photoURL)
                    .into(ivViewingPartyProfile)
                root.setOnClickListener {
                    readViewingParty(viewingPartyItem.id)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ViewingPartyListModel.ViewingPartyElementModel>() {
        override fun areItemsTheSame(oldItem: ViewingPartyListModel.ViewingPartyElementModel, newItem: ViewingPartyListModel.ViewingPartyElementModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ViewingPartyListModel.ViewingPartyElementModel, newItem: ViewingPartyListModel.ViewingPartyElementModel) =
            oldItem == newItem
    }
}