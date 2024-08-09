package umc.everyones.lck.presentation.match

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentTodayMatchBinding
import umc.everyones.lck.databinding.FragmentTodayMatchLckPogBinding
import umc.everyones.lck.domain.model.todayMatch.LckPog
import umc.everyones.lck.domain.model.todayMatch.Player
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.match.adapter.LckPogMatchRVA

@AndroidEntryPoint
class TodayMatchLckPogFragment : BaseFragment<FragmentTodayMatchLckPogBinding>(R.layout.fragment_today_match_lck_pog) {
    override fun initObserver() {

    }

    override fun initView() {
        val matches = listOf(
            LckPog(
                matchTitle = "LCK Summer 1st Match",
                matchDate = "2024.07.12. 17:00",
                players = listOf(
                    Player(name = "Deft", profileImageResId = R.drawable.ic_profile_player),
                    Player(name = "Faker", profileImageResId = R.drawable.ic_profile_player),
                    Player(name = "Chovy", profileImageResId = R.drawable.ic_profile_player)
                )
            ),
            LckPog(
                matchTitle = "LCK Summer 2nd Match",
                matchDate = "2024.07.13. 19:00",
                players = listOf(
                    Player(name = "Ruler", profileImageResId = R.drawable.ic_profile_player),
                    Player(name = "ShowMaker", profileImageResId = R.drawable.ic_profile_player),
                    Player(name = "Canyon", profileImageResId = R.drawable.ic_profile_player)
                )
            )
        )

        val adapter = LckPogMatchRVA(matches)
        binding.rvTodayMatchLckPogContainer.layoutManager = LinearLayoutManager(context)
        binding.rvTodayMatchLckPogContainer.adapter = adapter
    }
}