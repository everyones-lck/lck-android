package umc.everyones.lck.presentation.party.read

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentReadViewingPartyBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.party.ViewingPartyChatActivity
import umc.everyones.lck.presentation.party.dialog.JoinViewingPartyDialogFragment
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.showCustomSnackBar

@AndroidEntryPoint
class ReadViewingPartyFragment : BaseFragment<FragmentReadViewingPartyBinding>(R.layout.fragment_read_viewing_party) {
    private val viewModel: ReadViewingPartyViewModel by viewModels()
    private val navigator by lazy {
        findNavController()
    }

    private val args: ReadViewingPartyFragmentArgs by navArgs()

    private val postId by lazy {
        args.postId
    }

    private val isWriter by lazy {
        args.isWriter
    }
    override fun initObserver() {
        repeatOnStarted {
            viewModel.readViewingPartyEvent.collect{ event ->
                handleReadViewingPartyEvent(event)
            }
        }
    }

    private fun handleReadViewingPartyEvent(event: ReadViewingPartyViewModel.ReadViewingPartyEvent){
        when(event){
            is ReadViewingPartyViewModel.ReadViewingPartyEvent.Fail -> {
                showCustomSnackBar(binding.root, event.message)
            }
            is ReadViewingPartyViewModel.ReadViewingPartyEvent.Read -> {

            }
            is ReadViewingPartyViewModel.ReadViewingPartyEvent.Success -> {
                if(event.message.isNotEmpty()){
                    showCustomSnackBar(binding.root, event.message)
                }
            }
        }
    }

    override fun initView() {
        Log.d("postId", postId.toString())
        distinguishView()
        binding.ivReadBackBtn.setOnClickListener {
            navigator.navigateUp()
        }
    }

    private fun distinguishView(){
        if(!isWriter) {
            with(binding){
                groupReadWriterMenu.visibility = View.GONE
                tvReadParticipantCancelGuide.visibility = View.VISIBLE
                tvReadAskToHost.text = "주최자에게 질문하기"
                tvReadJoinViewingParty.text = "Viewing Party 참여하기"
                askToHost()
                joinViewingParty()
            }
        } else {
            inquireParticipantsList()
        }
    }

    private fun joinViewingParty(){
        binding.tvReadJoinViewingParty.setOnClickListener {
            viewModel.setTitle(binding.tvReadViewingPartyTitle.text.toString())
            val dialog = JoinViewingPartyDialogFragment()
            dialog.setOnJoinViewingPartyClickListener(object : JoinViewingPartyDialogFragment.OnJoinViewingPartyClickListener{
                override fun onConfirm() {
                    viewModel.joinViewingParty()
                }
            })
            dialog.show(childFragmentManager, dialog.tag)
        }
    }

    private fun inquireParticipantsList(){
        binding.tvReadAskToHost.setOnClickListener {
            navigator.navigate(R.id.action_readViewingPartyFragment_to_participantsFragment)
        }
    }

    private fun askToHost(){
        binding.tvReadAskToHost.setOnClickListener {
            startActivity(ViewingPartyChatActivity.newIntent(requireContext()))
        }
    }
}