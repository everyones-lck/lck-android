package umc.everyones.lck.presentation.lck

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
        val matchDetailsList = listOf(
            listOf(
                MatchData("2024 LCK Summer 1st Match", "17:00", R.drawable.ic_gen_g, R.drawable.ic_t1),
                MatchData("2024 LCK Summer 2nd Match", "18:00", R.drawable.ic_gen_g, R.drawable.ic_t1)
            ),
            listOf(
                MatchData("2024 LCK Summer 3rd Match", "Win | Gene.G", R.drawable.ic_gen_g, R.drawable.img_about_lck_t1_gray),
                MatchData("2024 LCK Summer 4th Match", "Win | T1", R.drawable.img_about_lck_gen_g_gray, R.drawable.ic_t1)
            ),
            listOf(
                MatchData("2024 LCK Summer 5nd Match", "18:00", R.drawable.ic_gen_g, R.drawable.ic_t1)
            ),
            listOf(
                MatchData("-","No Match",null,null)
            )
        )

        for (details in matchDetailsList) {
            matchVPAdapter.addMatchDetails(details)
        }

        viewPager.adapter = matchVPAdapter

        val pageMarginPx = 16 * resources.displayMetrics.densityDpi / 160
        val offsetPx = 40 * resources.displayMetrics.densityDpi / 160

        viewPager.setPageTransformer { page, position ->
            page.translationX = -offsetPx * position
        }

        viewPager.offscreenPageLimit = 1
        val recyclerViewMatches = viewPager.getChildAt(0) as RecyclerView
        recyclerViewMatches.setPadding(pageMarginPx, 0, pageMarginPx, 0)
        recyclerViewMatches.clipToPadding = false

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
