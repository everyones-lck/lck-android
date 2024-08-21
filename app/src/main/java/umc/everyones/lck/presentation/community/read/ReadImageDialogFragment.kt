package umc.everyones.lck.presentation.community.read

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogMediaBinding
import umc.everyones.lck.presentation.base.BaseDialogFragment
import umc.everyones.lck.presentation.party.read.ReadViewingPartyViewModel
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnSingleClickListener

class ReadImageDialogFragment : BaseDialogFragment<DialogMediaBinding>(R.layout.dialog_media) {
    private val viewModel: ReadPostViewModel by activityViewModels()

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.imageUrl.collect{
                if (it.isEmpty()){
                    return@collect
                }
                with(binding){
                    Glide.with(requireContext())
                        .load(it)
                        .into(ivImage)
                }
            }
        }
    }

    override fun initView() {
        requireContext().dialogFragmentResize(this, 0.8f, 0.8f)
        binding.ivClose.setOnSingleClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}