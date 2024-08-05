package umc.everyones.lck.presentation.lck

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckBinding
import umc.everyones.lck.presentation.base.BaseFragment
import umc.everyones.lck.presentation.lck.adapter.MatchVPAdapter
import umc.everyones.lck.presentation.lck.adapter.RankingAdapter
import umc.everyones.lck.presentation.lck.data.MatchData
import umc.everyones.lck.presentation.lck.data.RankingData
import umc.everyones.lck.presentation.lck.util.CustomDatePickerDialog
import umc.everyones.lck.presentation.lck.util.OnTeamClickListener
import umc.everyones.lck.presentation.lck.util.VerticalSpaceItemDecoration
import java.util.Calendar

@AndroidEntryPoint
class AboutLCKFragment : BaseFragment<FragmentAboutLckBinding>(R.layout.fragment_about_lck),
    OnTeamClickListener {

    private lateinit var matchVPAdapter: MatchVPAdapter
    private lateinit var rankingAdapter: RankingAdapter

    private var isDatePickerDialogVisible = false
    private var datePickerDialog: CustomDatePickerDialog? = null

    override fun initObserver() {
    }

    override fun initView() {
        initMatchViewPager()
        initRankingRecyclerView()
        initBackButton()
        initCalendarButton()
    }

    private fun initMatchViewPager() {
        val viewPager: ViewPager2 = binding.vpAboutLckMatch
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
                MatchData("-", "No Match", null, null)
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
    }

    private fun initRankingRecyclerView() {
        val recyclerView: RecyclerView = binding.rvAboutLckRanking
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val teams = listOf(
            RankingData(R.drawable.ic_dplus_kia, "DK"),
            RankingData(R.drawable.ic_hanhwa, "HLE"),
            RankingData(R.drawable.ic_drx, "DRX"),
            RankingData(R.drawable.ic_nongshim_red_force, "NS"),
            RankingData(R.drawable.ic_bnk, "BNK"),
            RankingData(R.drawable.ic_ok_saving_bank_brion, "BRO"),
            RankingData(R.drawable.ic_kt_rolster, "KT")
        )

        rankingAdapter = RankingAdapter(teams, this)
        recyclerView.adapter = rankingAdapter

        val verticalSpaceHeightPx = (10 * resources.displayMetrics.density).toInt()
        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(verticalSpaceHeightPx))
    }

    private fun initBackButton() {
        binding.viewAboutLckRect1.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.fcv_about_lck_to_team_container, AboutLckTeamFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.viewAboutLckRect2.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.fcv_about_lck_to_team_container, AboutLckTeamFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.viewAboutLckRect3.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.fcv_about_lck_to_team_container, AboutLckTeamFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun initCalendarButton() {
        binding.ivAboutLckCalendar.setOnClickListener {
            toggleDatePickerDialog()
        }
        // 오늘 날짜를 초기값으로 설정
        val calendar = Calendar.getInstance()
        updateSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }

    private fun toggleDatePickerDialog() {
        if (isDatePickerDialogVisible) {
            datePickerDialog?.dismiss()
            isDatePickerDialogVisible = false
        } else {
            showCustomDatePickerDialog()
        }
    }

    private fun showCustomDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = CustomDatePickerDialog(
            requireContext(),
            year,
            month,
            day
        ) { selectedYear, selectedMonth, selectedDay ->
            updateSelectedDate(selectedYear, selectedMonth, selectedDay)
        }

        datePickerDialog?.show()
        isDatePickerDialogVisible = true
    }

    private fun updateSelectedDate(year: Int, month: Int, day: Int) {
        val dateTextView: TextView = binding.tvAboutLckDate
        val formattedDate = String.format("%d.%02d.%02d", year, month + 1, day)
        dateTextView.text = formattedDate
    }

    override fun onTeamClick(team: RankingData) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fcv_about_lck_to_team_container, AboutLckTeamFragment())
            .addToBackStack(null)
            .commit()
    }
}
