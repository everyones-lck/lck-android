package umc.everyones.lck.presentation.party.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.databinding.ItemParticipantBinding
import umc.everyones.lck.databinding.ItemViewingPartyBinding
import umc.everyones.lck.domain.model.party.ParticipantItem
import umc.everyones.lck.domain.model.party.ViewingPartyItem
import umc.everyones.lck.util.extension.setOnSingleClickListener

class ParticipantsRVA(val goToChat: () -> Unit) :
    ListAdapter<ParticipantItem, ParticipantsRVA.ParticipantViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        return ParticipantViewHolder(
            ItemParticipantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ParticipantViewHolder(private val binding: ItemParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(participantItem: ParticipantItem) {
            with(binding){
                /*Glide.with(ivParticipantProfileImage.context)
                    .load(participantItem.profileImage)
                    .into(ivParticipantProfileImage)

                tvParticipantName.text = participantItem.name
                tvParticipantFavoriteTeam.text = participantItem.favoriteTeam*/
                ivParticipantChatBtn.setOnSingleClickListener {
                    goToChat()
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ParticipantItem>() {
        override fun areItemsTheSame(oldItem: ParticipantItem, newItem: ParticipantItem) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: ParticipantItem, newItem: ParticipantItem) =
            oldItem == newItem
    }
}