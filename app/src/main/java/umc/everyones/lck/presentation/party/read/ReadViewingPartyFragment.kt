package umc.everyones.lck.presentation.party.read

import android.annotation.SuppressLint
import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentReadViewingPartyBinding
import umc.everyones.lck.domain.model.request.party.WriteViewingPartyModel
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.party.ViewingPartyViewModel
import umc.everyones.lck.presentation.party.chat.ViewingPartyChatActivity
import umc.everyones.lck.presentation.party.dialog.JoinViewingPartyDialogFragment
import umc.everyones.lck.presentation.party.write.WriteViewingPartyActivity
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnSingleClickListener
import umc.everyones.lck.util.extension.showCustomSnackBar
import umc.everyones.lck.util.extension.textToString
import umc.everyones.lck.util.extension.toWriteViewingPartyDateFormat
import umc.everyones.lck.util.network.UiState
import javax.inject.Inject

@AndroidEntryPoint
class ReadViewingPartyFragment : BaseFragment<FragmentReadViewingPartyBinding>(R.layout.fragment_read_viewing_party) {
    private val viewModel: ReadViewingPartyViewModel by activityViewModels()
    private val viewingPartyViewModel: ViewingPartyViewModel by activityViewModels()
    private val navigator by lazy {
        findNavController()
    }

    private val args: ReadViewingPartyFragmentArgs by navArgs()

    private val postId by lazy {
        args.postId
    }

    private val shortLocation by lazy {
        args.shortLocation
    }

    private var writeResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val isWriteDone = result.data?.getBooleanExtra("isWriteDone", false) ?: false
            if(isWriteDone){
                viewingPartyViewModel.setIsRefreshNeeded(true)
                navigator.navigateUp()
            }
        }
    }
    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.readViewingPartyEvent.collect{ state ->
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
            viewModel.isWriter.collect{ isWriter ->
                Log.d("iSwrite", isWriter.toString())
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
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleReadViewingPartyEvent(event: ReadViewingPartyViewModel.ReadViewingPartyEvent){
        when(event){
            is ReadViewingPartyViewModel.ReadViewingPartyEvent.ReadViewingParty -> {
                with(binding){
                    tvReadViewingPartyTitle.text = event.viewingParty.name
                    tvReadQualify.text = "To. ${event.viewingParty.qualify}"
                    tvReadDate.text = event.viewingParty.partyDate
                    tvReadPlace.text = event.viewingParty.place
                    tvReadPrice.text = "₩${event.viewingParty.price}"
                    tvReadParticipants.text = event.viewingParty.participants
                    tvReadEtc.text = event.viewingParty.etc
                    tvReadWriter.text = event.viewingParty.writerInfo
                    Glide.with(requireContext())
                        .load(event.viewingParty.ownerImage)
                        .into(ivReadProfileImage)

                    viewModel.setTitle(event.viewingParty.name)
                }
            }

            ReadViewingPartyViewModel.ReadViewingPartyEvent.DeleteViewingParty -> {
                viewingPartyViewModel.setIsRefreshNeeded(true)
                navigator.navigateUp()
            }
            ReadViewingPartyViewModel.ReadViewingPartyEvent.JoinViewingParty -> {
                showCustomSnackBar(binding.root, "뷰잉파티에 참여되었습니다!")
            }

            is ReadViewingPartyViewModel.ReadViewingPartyEvent.WriteDoneViewingParty -> {
                if(event.isWriteDone){
                    navigator.navigateUp()
                }
            }

            else -> Unit
        }
    }

    override fun initView() {
        Log.d("postId", postId.toString())
        viewModel.setPostId(postId)
        viewModel.fetchViewingParty()
        goToEditViewingParty()
        binding.ivReadBackBtn.setOnSingleClickListener {
            navigator.navigateUp()
        }
    }
    private fun deleteViewingParty(){
        binding.ivReadDeleteBtn.setOnSingleClickListener {
            viewModel.deleteViewingParty()
        }
    }

    private fun joinViewingParty(){
        binding.tvReadJoinViewingParty.setOnSingleClickListener {
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
        binding.tvReadAskToHost.setOnSingleClickListener {
            navigator.navigate(R.id.action_readViewingPartyFragment_to_participantsFragment)
        }
    }

    private fun askToHost(){
        binding.tvReadAskToHost.setOnSingleClickListener {
            startActivity(ViewingPartyChatActivity.newIntent(requireContext(), postId, true, ""))
        }
    }

    private fun goToEditViewingParty(){
        with(binding){
            ivReadEditBtn.setOnSingleClickListener {
                val participate = tvReadParticipants.textToString().split("-")
                writeResultLauncher.launch(WriteViewingPartyActivity.editIntent(requireContext(), postId,
                    WriteViewingPartyModel(
                        name = tvReadViewingPartyTitle.textToString(),
                        date = tvReadDate.textToString().toWriteViewingPartyDateFormat(),
                        latitude = 0.0,
                        longitude = 0.0,
                        price = tvReadPrice.textToString().replace("₩", "").trim(),
                        lowParticipate = participate[0].trim(),
                        highParticipate = participate[1].replace(("[^\\d]").toRegex(), ""),
                        qualify = tvReadQualify.textToString().replace("To.", ""),
                        etc = tvReadEtc.textToString(),
                        location = tvReadPlace.textToString(),
                        shortLocation = shortLocation
                    )
                ))
            }
        }
    }
}