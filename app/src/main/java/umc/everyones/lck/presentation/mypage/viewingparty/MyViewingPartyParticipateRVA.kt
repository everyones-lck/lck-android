package umc.everyones.lck.presentation.mypage.viewingparty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.lck.databinding.ItemMypageCommunityBinding
import umc.everyones.lck.domain.model.response.mypage.ParticipateViewingPartyMypageModel
import umc.everyones.lck.util.extension.setOnSingleClickListener

class MyViewingPartyParticipateRVA(
    val readViewingParty: (Long) -> Unit,
    val deleteViewingParty: (Long) -> Unit,
    private val showBottomSheet: (Long, String) -> Unit
) : PagingDataAdapter<ParticipateViewingPartyMypageModel.ParticipateViewingPartyMypageElementModel, MyViewingPartyParticipateRVA.ViewingPartyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewingPartyViewHolder {
        return ViewingPartyViewHolder(
            ItemMypageCommunityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            readViewingParty = readViewingParty, // 클릭 리스너 전달
            deleteViewingParty = deleteViewingParty // 삭제 콜백 전달
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
        private val deleteViewingParty: (Long) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewingPartyItem: ParticipateViewingPartyMypageModel.ParticipateViewingPartyMypageElementModel) {
            with(binding) {
                tvMypageCommunityTitle.text = viewingPartyItem.name
                tvMypageCommunityCategory.text = viewingPartyItem.date

                root.setOnClickListener { // 아이템 전체 클릭 리스너
                    val action = MyPageViewingPartyFragmentDirections.actionMyPageViewingPartyFragmentToViewingPartyGuestBottomSheetFragment(viewingPartyItem.id, viewingPartyItem.name)
                    root.findNavController().navigate(action)
                }

                // 기존의 바로가기 버튼 클릭 리스너는 유지하거나 필요에 따라 제거
                tvMypageCommunityShortcuts.setOnSingleClickListener {
                    readViewingParty(viewingPartyItem.id)
                }
            }
        }
    }
    class DiffCallback : DiffUtil.ItemCallback<ParticipateViewingPartyMypageModel.ParticipateViewingPartyMypageElementModel>() {
        override fun areItemsTheSame(oldItem: ParticipateViewingPartyMypageModel.ParticipateViewingPartyMypageElementModel, newItem: ParticipateViewingPartyMypageModel.ParticipateViewingPartyMypageElementModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ParticipateViewingPartyMypageModel.ParticipateViewingPartyMypageElementModel, newItem: ParticipateViewingPartyMypageModel.ParticipateViewingPartyMypageElementModel) =
            oldItem == newItem
    }
}
