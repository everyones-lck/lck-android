package umc.everyones.lck.presentation.home

import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentHomeBinding
import umc.everyones.lck.presentation.base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun initObserver() {

    }

    override fun initView() {
        goTodayMatch()
        goMatchResult()
        goAboutLck()
        goCommunity()
        goViewingParty()
    }

    private fun goTodayMatch() {
        binding.ivHomeTodayMatchBox.setOnClickListener {
            homeViewModel.setNavigateEvent(R.id.todayMatchFragment)
        }
    }

    private fun goMatchResult() {
        binding.layoutHomeMatchResult.setOnClickListener {
            homeViewModel.setNavigateEvent(R.id.aboutLCKFragment)
        }
    }

    private fun goAboutLck() {
        binding.ivHomeAboutLckBox.setOnClickListener {
            homeViewModel.setNavigateEvent(R.id.aboutLCKFragment)
        }
    }

    private fun goCommunity() {
        binding.ivHomeCommunityBox.setOnClickListener {
            homeViewModel.setNavigateEvent(R.id.boardFragment)
        }
    }

    private fun goViewingParty() {
        binding.ivHomeViewingPartyBox.setOnClickListener {
            homeViewModel.setNavigateEvent(R.id.viewingPartyFragment)
        }
    }
}