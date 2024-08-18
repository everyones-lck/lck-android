package umc.everyones.lck.presentation.match

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentTodayMatchLckMatchBinding
import umc.everyones.lck.domain.model.todayMatch.LckMatch
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.match.adapter.LckMatchContentRVA

@AndroidEntryPoint
class TodayMatchLckMatchFragment : BaseFragment<FragmentTodayMatchLckMatchBinding>(R.layout.fragment_today_match_lck_match) {
    private val viewModel: TodayMatchLckMatchViewModel by viewModels()
    private lateinit var lckMatchContentRVA: LckMatchContentRVA
    override fun initObserver() {

    }

    override fun initView() {
        Log.d("TodayMatchLckMatchFragment", "initView called")
        lckMatchRecycler()
        viewModel.fetchTodayMatchInformation()
    }

    private val matches = listOf(
        LckMatch(
            matchTitle = "LCK Summer 1st Match",
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

    private fun lckMatchRecycler() {
        lckMatchContentRVA = LckMatchContentRVA(matches) { team1LogoResId, team2LogoResId ->
            // 팀 로고 리소스 ID를 번들에 담아 전달
            val bundle = Bundle().apply {
                putIntArray("teamLogos", intArrayOf(team1LogoResId, team2LogoResId))
            }
            // 네비게이션을 통해 TodayMatchPredictionFragment로 이동하며 번들 전달
            findNavController().navigate(R.id.todayMatchPredictionFragment, bundle)
        }
        binding.rvTodayMatchLckMatchContainer.layoutManager = LinearLayoutManager(context)
        binding.rvTodayMatchLckMatchContainer.adapter = lckMatchContentRVA
    }

}