package umc.everyones.lck.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentHomeBinding
import umc.everyones.lck.domain.model.todayMatch.LckMatch
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.login.LoginActivity
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.util.LoginManager
import umc.everyones.lck.presentation.home.adapter.HomeMatchContentVPA
import umc.everyones.lck.presentation.home.adapter.HomeMatchResultRVA

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun initObserver() {

    }

    override fun initView() {
        val matches = listOf(
            LckMatch(
                matchTitle = "2024 LCK Summer 1st Match",
                matchDate = "2024.07.12. 17:00",
                team1Name = "Gen.G",
                team2Name = "T1",
                team1LogoResId = R.drawable.ic_gen_g,
                team1LogoBlur = R.drawable.ic_geng_blurred,
                team2LogoResId = R.drawable.ic_t1,
                team2LogoBlur = R.drawable.ic_t1_blurred,
                team1WinRate = "60%",
                team2WinRate = "40%"
            ),
            LckMatch(
                matchTitle = "LCK Summer 2nd Match",
                matchDate = "2024.07.12. 20:00",
                team1Name = "DRX",
                team2Name = "DK",
                team1LogoResId = R.drawable.ic_drx,
                team1LogoBlur = R.drawable.ic_drx_blurred,
                team2LogoResId = R.drawable.ic_dplus_kia,
                team2LogoBlur = R.drawable.ic_dk_blurred,
                team1WinRate = "37%",
                team2WinRate = "63%"
            )
        )

        val homeMatchContentVPA = HomeMatchContentVPA(matches) {
            homeViewModel.setNavigateEvent(R.id.todayMatchTab)
        }
        binding.vpHomeMatchContent.adapter = homeMatchContentVPA
        binding.indicatorHomeDot.attachTo(binding.vpHomeMatchContent)

        val homeMatchResultRVA = HomeMatchResultRVA(matches)
        binding.rvHomeMatchResult.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHomeMatchResult.setHasFixedSize(true)
        binding.rvHomeMatchResult.adapter = homeMatchResultRVA

        goMatchResult()
        goAboutLck()
        goCommunity()
        goViewingParty()
    }

    private fun goMatchResult() {
        binding.layoutHomeMatchResult.setOnClickListener {
            homeViewModel.setNavigateEvent(R.id.todayMatchTab)
        }
    }

    private fun goAboutLck() {
        binding.ivHomeAboutLckBox.setOnClickListener {
            homeViewModel.setNavigateEvent(R.id.aboutLCKFragment)
        }
    }

    private fun goCommunity() {
        binding.ivHomeCommunityBox.setOnClickListener {
            homeViewModel.setNavigateEvent(R.id.communityFragment)
        }
    }

    private fun goViewingParty() {
        binding.ivHomeViewingPartyBox.setOnClickListener {
            homeViewModel.setNavigateEvent(R.id.viewingPartyTab)
        }
    }

//    private fun setupMypageButton() {
//        binding.btnmypage.setOnClickListener {
//
//            val intent = Intent(requireContext(), MyPageActivity::class.java)
//            startActivity(intent)
//            requireActivity().finish() // Finish the current activity
//        }
//    }
}
