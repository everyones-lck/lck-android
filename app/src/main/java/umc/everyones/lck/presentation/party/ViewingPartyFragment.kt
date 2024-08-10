package umc.everyones.lck.presentation.party

import android.content.Intent
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentViewingPartyBinding
import umc.everyones.lck.domain.model.party.ViewingPartyItem
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.presentation.party.adapter.ViewingPartyRVA
import umc.everyones.lck.presentation.party.write.WriteViewingPartyActivity
import umc.everyones.lck.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class ViewingPartyFragment : BaseFragment<FragmentViewingPartyBinding>(R.layout.fragment_viewing_party) {
    private var _viewIngPartyRVA: ViewingPartyRVA? = null
    private val viewingPartyRVA get() = _viewIngPartyRVA

    private val navigator by lazy {
        findNavController()
    }

    override fun initObserver() {

    }

    override fun initView() {
        initViewingPartyRVAdapter()
        goToWriteViewingParty()
        goMyPage()
    }

    private fun goToWriteViewingParty(){
        binding.fabViewingPartyWrite.setOnSingleClickListener {
            startActivity(WriteViewingPartyActivity.newIntent(requireContext()))
        }
    }

    private fun initViewingPartyRVAdapter(){
        _viewIngPartyRVA = ViewingPartyRVA { postId, isWriter ->
            val action = ViewingPartyFragmentDirections.actionViewingPartyFragmentToReadViewingPartyFragment(postId, isWriter)
            navigator.navigate(action)
        }
        binding.rvViewingParty.adapter = viewingPartyRVA
        val list = listOf(
            ViewingPartyItem(0, "", "", "", "", "", true),
            ViewingPartyItem(0, "", "", "", "", ""),
            ViewingPartyItem(0, "", "", "", "", ""),
            ViewingPartyItem(0, "", "", "", "", ""),
            ViewingPartyItem(0, "", "", "", "", ""),
            ViewingPartyItem(0, "", "", "", "", ""),
            ViewingPartyItem(0, "", "", "", "", ""),
            ViewingPartyItem(0, "", "", "", "", ""),
        )
        viewingPartyRVA?.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewIngPartyRVA = null
    }

    private fun goMyPage(){
        binding.ivMyPage.setOnSingleClickListener {
            startActivity(MyPageActivity.newIntent(requireContext()))
        }
    }
}