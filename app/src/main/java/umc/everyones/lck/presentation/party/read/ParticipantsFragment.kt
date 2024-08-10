package umc.everyones.lck.presentation.party.read

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentParticipantsBinding
import umc.everyones.lck.domain.model.party.ParticipantItem
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.party.ViewingPartyChatActivity
import umc.everyones.lck.presentation.party.adapter.ParticipantsRVA

@AndroidEntryPoint
class ParticipantsFragment : BaseFragment<FragmentParticipantsBinding>(R.layout.fragment_participants) {
    private var _participantsRVA: ParticipantsRVA? = null
    private val participantsRVA get() = _participantsRVA

    private val navigator by lazy {
        findNavController()
    }
    override fun initObserver() {

    }

    override fun initView() {
        initParticipantRVAdapter()
        binding.ivParticipantsBackBtn.setOnClickListener {
            navigator.navigateUp()
        }
    }

    private fun initParticipantRVAdapter(){
        val list = listOf(
            ParticipantItem("", "", ""),
            ParticipantItem("", "", ""),
            ParticipantItem("", "", ""),
            ParticipantItem("", "", ""),
            ParticipantItem("", "", ""),
            ParticipantItem("", "", ""),
            ParticipantItem("", "", ""),
            ParticipantItem("", "", ""),
            ParticipantItem("", "", ""),
            ParticipantItem("", "", ""),
            ParticipantItem("", "", ""),
        )
        _participantsRVA = ParticipantsRVA{
            startActivity(ViewingPartyChatActivity.newIntent(requireContext()))
        }
        binding.rvParticipantsList.adapter = participantsRVA
        participantsRVA?.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _participantsRVA = null
    }
}