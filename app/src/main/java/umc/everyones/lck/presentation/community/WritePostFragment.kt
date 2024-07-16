package umc.everyones.lck.presentation.community

import android.net.Uri
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentWritePostBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.community.adapter.MediaRVA

class WritePostFragment : BaseFragment<FragmentWritePostBinding>(R.layout.fragment_write_post) {
    private var _mediaRVA: MediaRVA? = null
    private val mediaRVA get() = _mediaRVA
    override fun initObserver() {

    }

    override fun initView() {
        initMediaRVAdapter()
    }

    private fun initMediaRVAdapter() {
        _mediaRVA = MediaRVA {  }
        binding.rvWriteMedia.adapter = mediaRVA
        mediaRVA?.submitList(listOf(Uri.EMPTY))
    }
}