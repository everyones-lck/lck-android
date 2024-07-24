package umc.everyones.lck.presentation.party

import android.util.Log
import androidx.fragment.app.viewModels
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogJoinViewingPartyBinding
import umc.everyones.lck.presentation.base.BaseDialogFragment
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.showCustomSnackBar

class JoinViewingPartyDialogFragment: BaseDialogFragment<DialogJoinViewingPartyBinding>(R.layout.dialog_join_viewing_party) {
    private val viewModel: ReadViewingPartyViewModel by viewModels({requireParentFragment()})
    private var onJoinViewingPartyClickListener: OnJoinViewingPartyClickListener? = null
    fun setOnJoinViewingPartyClickListener(listener: OnJoinViewingPartyClickListener){
        onJoinViewingPartyClickListener = listener
    }

    override fun initObserver() {
        repeatOnStarted {
            viewModel.title.collect{ title ->
                Log.d("title", title)
                binding.tvJoinViewingPartyTitle.text = title
            }
        }
    }

    override fun initView() {
        requireContext().dialogFragmentResize(this, 0.8f, 0.2f)

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