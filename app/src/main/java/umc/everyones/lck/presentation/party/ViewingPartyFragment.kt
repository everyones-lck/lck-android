package umc.everyones.lck.presentation.party

import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentViewingPartyBinding
import umc.everyones.lck.domain.model.party.ViewingPartyItem
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class ViewingPartyFragment : BaseFragment<FragmentViewingPartyBinding>(R.layout.fragment_viewing_party) {
    private var _viewIngPartyRVA: ViewingPartyRVA? = null
    private val viewingPartyRVA get() = _viewIngPartyRVA
    override fun initObserver() {

    }

    override fun initView() {
        initViewingPartyRVAdapter()
    }

    private fun initViewingPartyRVAdapter(){
        _viewIngPartyRVA = ViewingPartyRVA {

        }
        binding.rvViewingParty.adapter = viewingPartyRVA
        val list = listOf(
            ViewingPartyItem(0, "", "", "", "", ""),
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
}