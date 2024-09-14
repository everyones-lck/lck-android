package umc.everyones.lck.presentation.party.dialog

import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogJoinViewingPartyBinding
import umc.everyones.lck.presentation.base.BaseDialogFragment
import umc.everyones.lck.presentation.party.read.ReadViewingPartyViewModel
import umc.everyones.lck.util.extension.repeatOnStarted

class JoinViewingPartyDialogFragment: BaseDialogFragment<DialogJoinViewingPartyBinding>(R.layout.dialog_join_viewing_party) {
    private val viewModel: ReadViewingPartyViewModel by activityViewModels()
    private var onJoinViewingPartyClickListener: OnJoinViewingPartyClickListener? = null
    fun setOnJoinViewingPartyClickListener(listener: OnJoinViewingPartyClickListener){
        onJoinViewingPartyClickListener = listener
    }

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.title.collect{ title ->
                binding.tvJoinViewingPartyTitle.text = title
            }
        }
    }

    override fun initView() {
        requireContext().dialogFragmentResize(this, 0.8f)

        with(binding){
            btnJoinViewingCancel.setOnClickListener {
                dismiss()
            }
            btnJoinViewingConfirm.setOnClickListener {
                onJoinViewingPartyClickListener?.onConfirm()
                dismiss()
            }
        }
    }

    interface OnJoinViewingPartyClickListener{
        fun onConfirm()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onJoinViewingPartyClickListener = null
    }
}