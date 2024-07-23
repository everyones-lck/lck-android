package umc.everyones.lck.presentation.lck

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckBinding
import umc.everyones.lck.presentation.base.BaseFragment

class AboutLCKFragment : BaseFragment<FragmentAboutLckBinding>(R.layout.fragment_about_lck) {

    private lateinit var viewPager: ViewPager2
    private lateinit var matchVPAdapter: MatchVPAdapter
    override fun initObserver() {

    }

    override fun initView() {
        viewPager = binding.vpAboutLckMatch
        matchVPAdapter = MatchVPAdapter()

        // 각 페이지에 들어갈 MatchData 객체를 생성
        val matchDataList = listOf(
            MatchData(
                R.drawable.ic_gen_g, R.drawable.ic_t1, "2024 LCK Summer 1st Match", "17:00",
                R.drawable.ic_gen_g, R.drawable.ic_t1, "2024 LCK Summer 2nd Match", "18:00"
            ),
            MatchData(
                R.drawable.ic_gen_g, R.drawable.ic_t1, "2024 LCK Summer 3rd Match", "Win | Gene.G",
                R.drawable.ic_gen_g, R.drawable.ic_t1, "2024 LCK Summer 2nd Match", "18:00"
            )
        )

        for (matchData in matchDataList) {
            matchVPAdapter.addMatchData(matchData)
        }

        viewPager.adapter = matchVPAdapter
    }
}