package umc.everyones.lck.presentation.lck

import VerticalSpaceItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckBinding
import umc.everyones.lck.presentation.base.BaseFragment

class AboutLCKFragment : BaseFragment<FragmentAboutLckBinding>(R.layout.fragment_about_lck) {

    private lateinit var viewPager: ViewPager2
    private lateinit var matchVPAdapter: MatchVPAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var rankingAdapter: RankingAdapter

    override fun initObserver() {
    }

    override fun initView() {
        viewPager = binding.vpAboutLckMatch
        recyclerView = binding.rvAboutLckRanking

        matchVPAdapter = MatchVPAdapter()

        // 각 페이지에 들어갈 MatchData 객체를 생성
        val matchDataList = listOf(
            MatchData(
                R.drawable.ic_gen_g, R.drawable.ic_t1, "2024 LCK Summer 1st Match", "17:00",
                R.drawable.ic_gen_g, R.drawable.ic_t1, "2024 LCK Summer 2nd Match", "18:00"
            ),
            MatchData(
                R.drawable.ic_gen_g, R.drawable.img_about_lck_t1_gray, "2024 LCK Summer 3rd Match", "Win | Gene.G",
                R.drawable.img_about_lck_gen_g_gray, R.drawable.ic_t1, "2024 LCK Summer 4nd Match", "Win | T1"
            )
        )

        for (matchData in matchDataList) {
            matchVPAdapter.addMatchData(matchData)
        }

        viewPager.adapter = matchVPAdapter

        val pageMarginPx = 16 * resources.displayMetrics.densityDpi / 160
        val offsetPx = 40 * resources.displayMetrics.densityDpi / 160

        viewPager.setPageTransformer { page, position ->
            page.translationX = -offsetPx * position
        }

        viewPager.offscreenPageLimit = 1
        val recyclerView_matches = viewPager.getChildAt(0) as RecyclerView
        recyclerView_matches.setPadding(pageMarginPx, 0, pageMarginPx, 0)
        recyclerView_matches.clipToPadding = false


        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        val teams = listOf(
            RankingData(R.drawable.ic_dplus_kia, "DK"),
            RankingData(R.drawable.ic_hanhwa, "한화생명"),
            RankingData(R.drawable.ic_drx, "DRX"),
            RankingData(R.drawable.ic_nongshim_red_force, "농심"),
            RankingData(R.drawable.ic_bnk, "BNK"),
            RankingData(R.drawable.ic_ok_saving_bank_brion, "OK저축은행"),
            RankingData(R.drawable.ic_kt_rolster, "KT")
        )

        rankingAdapter = RankingAdapter(teams)
        recyclerView.adapter = rankingAdapter

        val verticalSpaceHeightPx = (10 * resources.displayMetrics.density).toInt()
        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(verticalSpaceHeightPx))


        binding.viewAboutLckRect2.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_about_lck_container, AboutLckTeamFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
