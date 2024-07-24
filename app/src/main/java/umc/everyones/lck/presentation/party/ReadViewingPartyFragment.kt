package umc.everyones.lck.presentation.party

import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentReadViewingPartyBinding
import umc.everyones.lck.presentation.base.BaseFragment

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
        binding.ivReadBackBtn.setOnClickListener {
            navigator.navigateUp()
        }
    }
}