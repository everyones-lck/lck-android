package umc.everyones.lck.presentation.match

import android.text.TextUtils.replace
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentTodayMatchBinding
import umc.everyones.lck.databinding.FragmentTodayMatchLckMatchBinding
import umc.everyones.lck.domain.model.todayMatch.LckMatch
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.match.adapter.LckMatchContentRVA

@AndroidEntryPoint
class TodayMatchLckMatchFragment : BaseFragment<FragmentTodayMatchLckMatchBinding>(R.layout.fragment_today_match_lck_match) {
    private val todayMatchViewModel: TodayMatchViewModel by activityViewModels()
    override fun initObserver() {

    }

    override fun initView() {
        val matches = listOf(
            LckMatch(
                matchTitle = "LCK Summer 1st Match",
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

        val adapter = LckMatchContentRVA(matches)
        binding.rvTodayMatchLckMatchContainer.layoutManager = LinearLayoutManager(context)
        binding.rvTodayMatchLckMatchContainer.adapter = adapter

    }

}