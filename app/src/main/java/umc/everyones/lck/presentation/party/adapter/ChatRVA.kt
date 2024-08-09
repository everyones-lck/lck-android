package umc.everyones.lck.presentation.party.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemParticipantBinding
import umc.everyones.lck.databinding.ItemReceiverChatBinding
import umc.everyones.lck.databinding.ItemSenderChatBinding
import umc.everyones.lck.domain.model.party.ChatItem
import umc.everyones.lck.domain.model.party.ParticipantItem

class ChatRVA :
    ListAdapter<ChatItem, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            SENDER -> SenderChatViewHolder(ItemSenderChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            RECEIVER -> ReceiverChatViewHolder(ItemReceiverChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalStateException("Invalid position")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SenderChatViewHolder -> {
                holder.bind(currentList[position])
            }
            is ReceiverChatViewHolder -> {
                holder.bind(currentList[position])
            }
        }
    }

    inner class SenderChatViewHolder(private val binding: ItemSenderChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatItem) {
            with(binding){
                /*Glide.with(ivParticipantProfileImage.context)
                    .load(participantItem.profileImage)
                    .into(ivParticipantProfileImage)

                tvParticipantName.text = participantItem.name
                tvParticipantFavoriteTeam.text = participantItem.favoriteTeam*/
            }
        }
    }

    inner class ReceiverChatViewHolder(private val binding: ItemReceiverChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatItem) {
            with(binding){
                /*Glide.with(ivParticipantProfileImage.context)
                    .load(participantItem.profileImage)
                    .into(ivParticipantProfileImage)

                tvParticipantName.text = participantItem.name
                tvParticipantFavoriteTeam.text = participantItem.favoriteTeam*/
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }

    class DiffCallback : DiffUtil.ItemCallback<ChatItem>() {
        override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem) =
            oldItem.userId == newItem.userId

        override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem) =
            oldItem == newItem
    }

    companion object {
        const val SENDER = 0
        const val RECEIVER = 1
    }
}