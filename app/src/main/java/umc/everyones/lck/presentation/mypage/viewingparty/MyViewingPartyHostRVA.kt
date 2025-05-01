package umc.everyones.lck.presentation.mypage.viewingparty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.lck.R
import umc.everyones.lck.databinding.ItemMypageCommunityBinding
import umc.everyones.lck.databinding.ItemViewingPartyBinding
import umc.everyones.lck.domain.model.response.mypage.HostViewingPartyMypageModel
import umc.everyones.lck.domain.model.response.party.ViewingPartyListModel
import umc.everyones.lck.util.extension.setOnSingleClickListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MyViewingPartyHostRVA(
    val readViewingParty: (Long) -> Unit,
    val deleteViewingParty: (Long) -> Unit,
    val onEditViewingParty: (Long) -> Unit // 수정하기 콜백 추가
) : PagingDataAdapter<HostViewingPartyMypageModel.HostViewingPartyMypageElementModel, MyViewingPartyHostRVA.ViewingPartyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewingPartyViewHolder {
        return ViewingPartyViewHolder(
            ItemMypageCommunityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewingPartyViewHolder, position: Int) {
        val viewingParty = getItem(position)
        if (viewingParty != null) {
            holder.bind(viewingParty)
        }
    }

    inner class ViewingPartyViewHolder(private val binding: ItemMypageCommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewingPartyItem: HostViewingPartyMypageModel.HostViewingPartyMypageElementModel) {
            with(binding) {
                tvMypageCommunityTitle.text = viewingPartyItem.name
                tvMypageCommunityCategory.text = viewingPartyItem.date

                tvMypageCommunityShortcuts.setOnSingleClickListener {
                    readViewingParty(viewingPartyItem.id)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<HostViewingPartyMypageModel.HostViewingPartyMypageElementModel>() {
        override fun areItemsTheSame(oldItem: HostViewingPartyMypageModel.HostViewingPartyMypageElementModel, newItem: HostViewingPartyMypageModel.HostViewingPartyMypageElementModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: HostViewingPartyMypageModel.HostViewingPartyMypageElementModel, newItem: HostViewingPartyMypageModel.HostViewingPartyMypageElementModel) =
            oldItem == newItem
    }
}
