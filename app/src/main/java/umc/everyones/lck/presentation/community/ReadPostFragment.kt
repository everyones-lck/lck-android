package umc.everyones.lck.presentation.community

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentReadPostBinding
import umc.everyones.lck.presentation.base.BaseFragment

class ReadPostFragment : BaseFragment<FragmentReadPostBinding>(R.layout.fragment_read_post) {
    private val navigator by lazy {
        findNavController()
    }
    private val args: ReadPostFragmentArgs by navArgs()
    private val postId by lazy {
        args.postId
    }
    override fun initObserver() {

    }

    override fun initView() {
        Log.d("postId", postId.toString())
    }
}