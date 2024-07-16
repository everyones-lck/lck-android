package umc.everyones.lck.presentation.community

import android.net.Uri
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentWritePostBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.community.adapter.MediaRVA
import umc.everyones.lck.presentation.community.adapter.SpinnerAdapter

class WritePostFragment : BaseFragment<FragmentWritePostBinding>(R.layout.fragment_write_post) {
    private val navigator by lazy {
        findNavController()
    }
    private val writePostViewModel: WritePostViewModel by activityViewModels()

    private var _mediaRVA: MediaRVA? = null
    private val mediaRVA get() = _mediaRVA
    override fun initObserver() {

    }

    override fun initView() {
        initMediaRVAdapter()
        initSpinnerAdapter()
        writeDone()

        binding.ivWriteClose.setOnClickListener {
            navigator.navigateUp()
        }
    }

    private fun initMediaRVAdapter() {
        _mediaRVA = MediaRVA {  }
        binding.rvWriteMedia.adapter = mediaRVA
        mediaRVA?.submitList(listOf(Uri.EMPTY))
    }

    private fun initSpinnerAdapter() {
        binding.spinnerWriteCategory.adapter = SpinnerAdapter(requireContext(), spinnerList)
    }

    private fun writeDone(){
        binding.ivWriteDone.setOnClickListener {
            writePostViewModel.setSelectedCategory(binding.spinnerWriteCategory.selectedItem.toString())
            navigator.navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mediaRVA = null
    }

    companion object {
        private val spinnerList = listOf("잡담", "응원", "FA", "거래", "질문", "후기")
    }
}