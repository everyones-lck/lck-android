package umc.everyones.lck.presentation.mypage.viewingparty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemMypageCommunityBinding
import umc.everyones.lck.domain.model.response.mypage.HostViewingPartyMypageModel
import umc.everyones.lck.util.extension.setOnSingleClickListener

class MyViewingPartyHostRVA(
    val readViewingParty: (Long) -> Unit,
    val deleteViewingParty: (Long) -> Unit,
    val showBottomSheet: (Long, String) -> Unit
) : PagingDataAdapter<HostViewingPartyMypageModel.HostViewingPartyMypageElementModel, MyViewingPartyHostRVA.ViewingPartyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewingPartyViewHolder {
        return ViewingPartyViewHolder(
            ItemMypageCommunityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            readViewingParty = readViewingParty, // 클릭 리스너 전달
            showBottomSheet = showBottomSheet
        )
    }

    override fun onBindViewHolder(holder: ViewingPartyViewHolder, position: Int) {
        val viewingParty = getItem(position)
        if (viewingParty != null) {
            holder.bind(viewingParty)
        }
    }

    inner class ViewingPartyViewHolder(
        private val binding: ItemMypageCommunityBinding,
        private val readViewingParty: (Long) -> Unit,
        private val showBottomSheet: (Long, String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewingPartyItem: HostViewingPartyMypageModel.HostViewingPartyMypageElementModel) {
            with(binding) {
                tvMypageCommunityTitle.text = viewingPartyItem.name
                tvMypageCommunityCategory.text = viewingPartyItem.date

                root.setOnClickListener {
                    showBottomSheet(viewingPartyItem.id, viewingPartyItem.name)
                }
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
