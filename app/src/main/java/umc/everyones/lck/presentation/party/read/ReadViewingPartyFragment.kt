package umc.everyones.lck.presentation.party.read

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentReadViewingPartyBinding
import umc.everyones.lck.domain.model.request.party.WriteViewingPartyModel
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.party.ViewingPartyChatActivity
import umc.everyones.lck.presentation.party.dialog.JoinViewingPartyDialogFragment
import umc.everyones.lck.presentation.party.write.WriteViewingPartyActivity
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnSingleClickListener
import umc.everyones.lck.util.extension.showCustomSnackBar
import umc.everyones.lck.util.extension.textToString

@AndroidEntryPoint
class ReadViewingPartyFragment : BaseFragment<FragmentReadViewingPartyBinding>(R.layout.fragment_read_viewing_party) {
    private val viewModel: ReadViewingPartyViewModel by activityViewModels()
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

    @SuppressLint("SetTextI18n")
    private fun handleReadViewingPartyEvent(event: ReadViewingPartyViewModel.ReadViewingPartyEvent){
        when(event){
            is ReadViewingPartyViewModel.ReadViewingPartyEvent.Fail -> {
                showCustomSnackBar(binding.root, event.message)
            }
            is ReadViewingPartyViewModel.ReadViewingPartyEvent.Read -> {
                with(binding){
                    tvReadViewingPartyTitle.text = event.viewingParty.name
                    tvReadQualify.text = "To. ${event.viewingParty.qualify}"
                    tvReadDate.text = event.viewingParty.partyDate
                    tvReadPlace.text = event.viewingParty.place
                    tvReadPrice.text = event.viewingParty.price
                    tvReadParticipants.text = event.viewingParty.participants
                    tvReadEtc.text = event.viewingParty.etc
                    tvReadWriter.text = event.viewingParty.writerInfo
                    Glide.with(requireContext())
                        .load(event.viewingParty.ownerImage)
                        .into(ivReadProfileImage)
                }
            }
            is ReadViewingPartyViewModel.ReadViewingPartyEvent.Success -> {
                if(event.message.isNotEmpty()){
                    when(event.message){
                        "delete" -> navigator.navigateUp()
                        else -> showCustomSnackBar(binding.root, event.message)
                    }
                }
            }
        }
    }

    override fun initView() {
        Log.d("postId", postId.toString())
        viewModel.setPostId(2L)
        viewModel.fetchViewingParty()
        distinguishView()
        goToEditViewingParty()
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
            deleteViewingParty()
        }
    }

    private fun deleteViewingParty(){
        binding.ivReadDeleteBtn.setOnSingleClickListener {
            viewModel.deleteViewingParty()
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
            startActivity(ViewingPartyChatActivity.newIntent(requireContext(), postId))
        }
    }

    private fun goToEditViewingParty(){
        with(binding){
            ivReadEditBtn.setOnClickListener {
                val participate = tvReadParticipants.textToString().split("-")
                val date = tvReadDate.textToString().split(" ")
                startActivity(WriteViewingPartyActivity.editIntent(requireContext(), postId,
                    WriteViewingPartyModel(
                        name = tvReadViewingPartyTitle.textToString(),
                        date = "${date[0]} | ${date[1].trim()}",
                        latitude = 0.0,
                        longitude = 0.0,
                        price = tvReadPrice.textToString().replace("₩", ""),
                        lowParticipate = participate[0].trim(),
                        highParticipate = participate[1].replace(("[^\\d]").toRegex(), ""),
                        qualify = tvReadQualify.textToString().replace("To.", ""),
                        etc = tvReadEtc.textToString(),
                        location = tvReadPlace.textToString()
                    )
                ))
            }
        }
    }
}