package umc.everyones.lck.presentation.party.read

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentParticipantsBinding
import umc.everyones.lck.domain.model.party.ParticipantItem
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.party.chat.ViewingPartyChatActivity
import umc.everyones.lck.presentation.party.adapter.ParticipantsRVA
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnSingleClickListener
import umc.everyones.lck.util.extension.showCustomSnackBar
import umc.everyones.lck.util.network.UiState

@AndroidEntryPoint
class ParticipantsFragment : BaseFragment<FragmentParticipantsBinding>(R.layout.fragment_participants) {
    private var _participantsRVA: ParticipantsRVA? = null
    private val participantsRVA get() = _participantsRVA

    private val navigator by lazy {
        findNavController()
    }

    private val viewModel: ReadViewingPartyViewModel by activityViewModels()
    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.readViewingPartyEvent.collect { state ->
                when(state){
                    is UiState.Success -> {handleReadViewingPartyEvent(state.data)}
                    is UiState.Failure -> {
                        showCustomSnackBar(binding.root, state.msg)
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.viewingPartyParticipantsPage.collectLatest { data ->
                participantsRVA?.submitData(data)
            }
        }
    }

    private fun handleReadViewingPartyEvent(event: ReadViewingPartyViewModel.ReadViewingPartyEvent){
        when(event){
            is ReadViewingPartyViewModel.ReadViewingPartyEvent.ReadParticipants -> {
                with(binding){
                    tvParticipantsViewingPartyTitle.text = event.participants.viewingPartyName
                    tvParticipantsWriter.text = event.participants.ownerInfo
                    Glide.with(requireContext())
                        .load(event.participants.ownerImage)
                        .into(ivParticipantsProfileImage)
                }
            }
            else -> Unit
        }
    }

    override fun initView() {
        initParticipantRVAdapter()
        viewModel.fetchViewingPartyParticipants()
        binding.ivParticipantsBackBtn.setOnSingleClickListener {
            navigator.navigateUp()
        }
    }

    private fun initParticipantRVAdapter(){
        _participantsRVA = ParticipantsRVA{
            startActivity(ViewingPartyChatActivity.newIntent(requireContext(), viewModel.postId.value, false, it))
        }
        binding.rvParticipantsList.adapter = participantsRVA
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _participantsRVA = null
    }
}