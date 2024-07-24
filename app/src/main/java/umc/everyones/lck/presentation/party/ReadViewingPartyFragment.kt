package umc.everyones.lck.presentation.party

import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentReadViewingPartyBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.util.extension.showCustomSnackBar

class ReadViewingPartyFragment : BaseFragment<FragmentReadViewingPartyBinding>(R.layout.fragment_read_viewing_party) {
    private val navigator by lazy {
        findNavController()
    }

    private val args: ReadViewingPartyFragmentArgs by navArgs()

    private val postId by lazy {
        args.postId
    }
    override fun initObserver() {

    }

    override fun initView() {
        Log.d("postId", postId.toString())
        joinViewingParty()
        binding.ivReadBackBtn.setOnClickListener {
            navigator.navigateUp()
        }
    }

    private fun joinViewingParty(){
        binding.tvReadJoinViewingParty.setOnClickListener {
            val dialog = JoinViewingPartyDialogFragment()
            dialog.setOnJoinViewingPartyClickListener(object : JoinViewingPartyDialogFragment.OnJoinViewingPartyClickListener{
                override fun onConfirm() {
                    showCustomSnackBar(requireView(), "뷰잉파티에 참여되었습니다!")
                }
            })
            dialog.show(childFragmentManager, dialog.tag)
        }
    }
}