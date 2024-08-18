package umc.everyones.lck.presentation.lck

import android.util.Log
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckBinding
import umc.everyones.lck.domain.model.about_lck.AboutLckMatchDetailsModel
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

    private val navigator by lazy { findNavController() }
    private val viewModel: AboutLckViewModel by viewModels()

    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.matchDetails.collect { result ->
                handleMatchDetailsResult(result)
            }
        }
    }

    override fun initView() {
        initMatchViewPager()
        initRankingRecyclerView()
        initBackButton()
        initCalendarButton()
    }

    private fun handleMatchDetailsResult(result: Result<AboutLckMatchDetailsModel>?) {
        result?.onSuccess { data ->
            val matchDataList = data.matchDetailList.map { match ->
                createMatchData(match)
            }
            updateMatchViewPager(matchDataList, data.listSize)
        }?.onFailure {
            Log.e("AboutLCKFragment", "Failed to fetch match details")
        }
    }

    private fun createMatchData(match: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel): MatchData {
        val matchTime = if (match.matchFinished) {
            "Win | ${viewModel.getWinningTeamName(match)}"
        } else {
            match.matchTime.substring(0, 5) // "10:00:00" 형식 에서 "10:00"만 추출
        }

        Log.d("AboutLCKFragment", "Team1 Logo URL: ${match.team1.teamLogoUrl}")
        Log.d("AboutLCKFragment", "Team2 Logo URL: ${match.team2.teamLogoUrl}")

        return MatchData(
            matchTitle = viewModel.formatMatchTitle(match.season, match.matchNumber),
            matchTime = matchTime,
            teamLogoUrl1 = match.team1.teamLogoUrl,
            teamLogoUrl2 = match.team2.teamLogoUrl,
            isTeam1Winner = match.team1.winner,
            isTeam2Winner = match.team2.winner
        )
    }

    private fun updateMatchViewPager(matchDataList: List<MatchData>, listSize: Int) {
        matchVPAdapter = MatchVPAdapter()

        when (listSize) {
            2 -> {
                val matchDetailsList = matchDataList.chunked(2)
                matchDetailsList.forEach { matchVPAdapter.addMatchDetails(it) }
            }
            1 -> {
                matchVPAdapter.addMatchDetails(matchDataList)
            }
            0 -> {
                matchVPAdapter.addMatchDetails(
                    listOf(MatchData("-", "No Matches", null, null,false,false))
                )
            }
        }
        binding.vpAboutLckMatch.adapter = matchVPAdapter
    }

    private fun initMatchViewPager() {
        val viewPager: ViewPager2 = binding.vpAboutLckMatch
        matchVPAdapter = MatchVPAdapter()
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
            RankingData(4, R.drawable.ic_dplus_kia, "DK"),
            RankingData(5, R.drawable.ic_hanhwa, "HLE"),
            RankingData(6, R.drawable.ic_drx, "DRX"),
            RankingData(7, R.drawable.ic_nongshim_red_force, "NS"),
            RankingData(8, R.drawable.ic_bnk, "BNK"),
            RankingData(9, R.drawable.ic_ok_saving_bank_brion, "BRO"),
            RankingData(10, R.drawable.ic_kt_rolster, "KT")
        )

        rankingAdapter = RankingAdapter(teams, this)
        recyclerView.adapter = rankingAdapter

        val verticalSpaceHeightPx = (10 * resources.displayMetrics.density).toInt()
        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(verticalSpaceHeightPx))

        viewModel.fetchLckRanking("2024 Spring" , 5, 10)
    }

    private fun initBackButton() {
        binding.viewAboutLckRect1.setOnClickListener {
            navigator.navigate(R.id.action_aboutLCKFragment_to_aboutLckTeamFragment)
        }
        binding.viewAboutLckRect2.setOnClickListener {
            navigator.navigate(R.id.action_aboutLCKFragment_to_aboutLckTeamFragment)
        }
        binding.viewAboutLckRect3.setOnClickListener {
            navigator.navigate(R.id.action_aboutLCKFragment_to_aboutLckTeamFragment)
        }
    }

    private fun initCalendarButton() {
        binding.ivAboutLckCalendar.setOnClickListener {
            toggleDatePickerDialog()
        }

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
        val formattedDate = String.format("%d-%02d-%02d", year, month + 1, day)
        dateTextView.text = String.format("%d.%02d.%02d", year, month + 1, day)

        // 선택된 날짜로 API 호출
        viewModel.fetchLckMatchDetails(formattedDate)
    }

    override fun onTeamClick(team: RankingData) {
        navigator.navigate(R.id.action_aboutLCKFragment_to_aboutLckTeamFragment)
    }
}
