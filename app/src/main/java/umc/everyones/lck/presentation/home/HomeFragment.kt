package umc.everyones.lck.presentation.home

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentHomeBinding
import umc.everyones.lck.domain.model.todayMatch.LckMatch
import umc.everyones.lck.presentation.base.BaseFragment
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
                matchTitle = "Gen.G vs T1",
                matchDate = "2024.07.12. 17:00",
                team1Name = "Gen.G",
                team2Name = "T1",
                team1LogoResId = R.drawable.ic_gen_g,
                team2LogoResId = R.drawable.ic_t1,
                team1WinRate = "60%",
                team2WinRate = "40%"
            ),
            LckMatch(
                matchTitle = "LCK Summer 2nd Match",
                matchDate = "2024.07.12. 20:00",
                team1Name = "DRX",
                team2Name = "DK",
                team1LogoResId = R.drawable.ic_drx,
                team2LogoResId = R.drawable.ic_dplus_kia,
                team1WinRate = "37%",
                team2WinRate = "63%"
            )
        )

//        val homeMatchContentRVA = HomeMatchContentRVA(matches)
//        binding.rvHomeMatchContent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvHomeMatchContent.adapter = homeMatchContentRVA

        val homeMatchContentVPA = HomeMatchContentVPA(matches)
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