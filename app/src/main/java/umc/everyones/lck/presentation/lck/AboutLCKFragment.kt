package umc.everyones.lck.presentation.lck

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import umc.everyones.lck.R
import umc.everyones.lck.databinding.FragmentAboutLckBinding
import umc.everyones.lck.domain.model.about_lck.AboutLckMatchDetailsModel
import umc.everyones.lck.domain.model.about_lck.AboutLckRankingDetailsModel
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
    private val topTeams = mutableListOf<RankingData>()

    private var isDatePickerDialogVisible = false
    private var datePickerDialog: CustomDatePickerDialog? = null

    private val navigator by lazy { findNavController() }
    private val viewModel: AboutLckViewModel by viewModels()

    private var selectedDate: Calendar = Calendar.getInstance()
    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.matchDetails.collect { result ->
                Log.d("AboutLCKFragment", "Collect")
                handleMatchDetailsResult(result)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.rankingDetails.collect { result ->
                handleRankingDetailsResult(result)
            }
        }
    }

    override fun initView() {
        initMatchViewPager()
        initRankingRecyclerView()
        initBackButton()
        initCalendarButton()
        initViewPagerSwipe()
        updateDateTextView(selectedDate)
        fetchMatchDataForSelectedDate()
    }

    private fun handleMatchDetailsResult(result: Result<AboutLckMatchDetailsModel>?) {
        Log.d("AboutLCKFragment", "handleMatchDetailsResult called with result: $result")

        result?.onSuccess { data ->
            Log.d("AboutLCKFragment", "Successfully fetched match details")

            matchVPAdapter.clearMatchDetails()

            val matchDataList = matchVPAdapter.getMatchDataList().toMutableList()

            val mappedMatches = data.matchDetailList.map { match ->
                val matchData = createMatchData(match)
                Log.d("AboutLCKFragment", "Mapped match data: $matchData")
                matchData
            }

            matchDataList.addAll(mappedMatches)
            Log.d("AboutLCKFragment", "Final match data list after adding new data: $matchDataList")



            updateMatchViewPager(matchDataList)
        }?.onFailure { throwable ->
            Log.e("AboutLCKFragment", "Failed to fetch match details: ${throwable.message}", throwable)
        }
    }

    private fun createMatchData(match: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel): MatchData {
        val matchDate = match.matchDate
        val matchTime = if (match.matchFinished) {
            "Win | ${viewModel.getWinningTeamName(match)}"
        } else {
            match.matchTime.substring(0, 5)
        }

        Log.d("AboutLCKFragment", "Team1 Logo URL: ${match.team1.teamLogoUrl}")
        Log.d("AboutLCKFragment", "Team2 Logo URL: ${match.team2.teamLogoUrl}")

        return MatchData(
            matchDate = matchDate,
            matchTitle = viewModel.formatMatchTitle(match.season, match.matchNumber),
            matchTime = matchTime,
            teamLogoUrl1 = match.team1.teamLogoUrl,
            teamLogoUrl2 = match.team2.teamLogoUrl,
            isTeam1Winner = match.team1.winner,
            isTeam2Winner = match.team2.winner
        )
    }

    private fun updateMatchViewPager(matchDataList: List<MatchData>) {
        Log.d("AboutLCKFragment", "Updating MatchViewPager with matchDataList: $matchDataList")

        val groupedMatchData = matchDataList.groupBy { it.matchDate }
        val selectedDateString = String.format(
            "%d-%02d-%02d",
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH) + 1,
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )

        val dateRange = listOf(
            LocalDate.parse(selectedDateString).minusDays(1).toString(),  // 어제
            selectedDateString,  // 오늘
            LocalDate.parse(selectedDateString).plusDays(1).toString()  // 내일
        )

        Log.d("AboutLCKFragment", "Date range for ViewPager: $dateRange")

        val viewPagerDataList = mutableListOf<List<MatchData>>()
        dateRange.forEach { date ->
            val matches = groupedMatchData[date]
            Log.d("AboutLCKFragment", "Matches for date $date: $matches")

            if (matches.isNullOrEmpty()) {
                viewPagerDataList.add(
                    listOf(
                        MatchData(
                            date, "No Matches", null, null, null, false, false
                        )
                    )
                )
            } else {
                viewPagerDataList.add(matches)
            }
        }

        if (!::matchVPAdapter.isInitialized) {
            matchVPAdapter = MatchVPAdapter()
            binding.vpAboutLckMatch.adapter = matchVPAdapter
        }

        matchVPAdapter.clearMatchDetails()
        viewPagerDataList.forEach { matchDetailsList ->
            matchVPAdapter.addMatchDetails(matchDetailsList)
        }
        matchVPAdapter.notifyDataSetChanged()

        Log.d("AboutLCKFragment", "MatchViewPager updated with new data, setting current item to today")
        binding.vpAboutLckMatch.setCurrentItem(1, false)
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

        viewPager.offscreenPageLimit = 3

        val recyclerViewMatches = viewPager.getChildAt(0) as RecyclerView
        recyclerViewMatches.setPadding(pageMarginPx, 0, pageMarginPx, 0)
        recyclerViewMatches.clipToPadding = false
    }
    private fun updateDateTextView(date: Calendar) {
        val dateTextView: TextView = binding.tvAboutLckDate
        val formattedDate = String.format("%d.%02d.%02d", date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH))
        dateTextView.text = formattedDate
    }

    private fun fetchMatchDataForSelectedDate() {
        binding.vpAboutLckMatch.post {
            matchVPAdapter.clearMatchDetails()
            matchVPAdapter.notifyDataSetChanged()

            val formattedDate = String.format(
                "%d-%02d-%02d",
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH) + 1,
                selectedDate.get(Calendar.DAY_OF_MONTH)
            )
            Log.d("API_CALL", "Fetching match data for date: $formattedDate")
            viewModel.fetchLckMatchDetails(formattedDate)
        }
    }

    private fun initViewPagerSwipe() {
        binding.vpAboutLckMatch.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("ViewPagerSwipe", "Page selected: $position")

                when (position) {
                    0 -> {
                        Log.d("ViewPagerSwipe", "Swiped to previous date (Left)")
                        moveToPreviousDate()
                    }
                    2 -> {
                        Log.d("ViewPagerSwipe", "Swiped to next date (Right)")
                        moveToNextDate()
                    }
                    else -> {
                        Log.d("ViewPagerSwipe", "Currently on the selected date")
                    }
                }
            }
        })
    }

    private fun moveToPreviousDate() {
        selectedDate.add(Calendar.DAY_OF_MONTH, -1)
        updateDateTextView(selectedDate)
        fetchMatchDataForSelectedDate()
    }

    private fun moveToNextDate() {
        selectedDate.add(Calendar.DAY_OF_MONTH, +1)
        updateDateTextView(selectedDate)
        fetchMatchDataForSelectedDate()
    }

    private fun handleRankingDetailsResult(result: Result<AboutLckRankingDetailsModel>?) {
        result?.onSuccess { rankingDetails ->
            val teamDetails = rankingDetails.teamDetailList

            if (teamDetails.isNotEmpty()) {
                topTeams.clear()
                topTeams.addAll(teamDetails.take(3).map { teamDetail ->
                    RankingData(
                        teamId = teamDetail.teamId,
                        ranking = teamDetail.rating,
                        teamLogoUrl = teamDetail.teamLogoUrl,
                        teamName = teamDetail.teamName
                    )
                })
                displayTopTeams(topTeams)
                val remainingTeams = teamDetails.drop(3).map { teamDetail ->
                    RankingData(
                        teamId = teamDetail.teamId,
                        ranking = teamDetail.rating,
                        teamLogoUrl = teamDetail.teamLogoUrl,
                        teamName = teamDetail.teamName
                    )
                }
                rankingAdapter.updateTeams(remainingTeams)
            }
        }?.onFailure {
            Log.e("AboutLCKFragment", "Failed to fetch ranking details")
        }
    }

    private fun initRankingRecyclerView() {
        val recyclerView: RecyclerView = binding.rvAboutLckRanking
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        rankingAdapter = RankingAdapter(mutableListOf(), this)
        recyclerView.adapter = rankingAdapter

        val verticalSpaceHeightPx = (10 * resources.displayMetrics.density).toInt()
        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(verticalSpaceHeightPx))

        viewModel.fetchLckRanking("2024 Summer", 0, 10)

    }

    private fun displayTopTeams(topTeams: List<RankingData>) {
        binding.ivAboutLckRanking1st.loadImage(topTeams[0].teamLogoUrl)
        binding.tvAboutLckRanking1st.text = topTeams[0].teamName

        binding.ivAboutLckRanking2nd.loadImage(topTeams[1].teamLogoUrl)
        binding.tvAboutLckRanking2nd.text = topTeams[1].teamName

        binding.ivAboutLckRanking3rd.loadImage(topTeams[2].teamLogoUrl)
        binding.tvAboutLckRanking3rd.text = topTeams[2].teamName
    }

    private fun ImageView.loadImage(url: String) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }


    private fun initBackButton() {
        binding.viewAboutLckRect1.setOnClickListener {
            topTeams.getOrNull(0)?.let { team ->
                val action = AboutLCKFragmentDirections.actionAboutLCKFragmentToAboutLckTeamFragment(
                    team.teamId,
                    team.teamName,
                    team.teamLogoUrl
                )
                navigator.navigate(action)
            }
        }

        binding.viewAboutLckRect2.setOnClickListener {
            topTeams.getOrNull(1)?.let { team ->
                val action = AboutLCKFragmentDirections.actionAboutLCKFragmentToAboutLckTeamFragment(
                    team.teamId,
                    team.teamName,
                    team.teamLogoUrl
                )
                navigator.navigate(action)
            }
        }

        binding.viewAboutLckRect3.setOnClickListener {
            topTeams.getOrNull(2)?.let { team ->
                val action = AboutLCKFragmentDirections.actionAboutLCKFragmentToAboutLckTeamFragment(
                    team.teamId,
                    team.teamName,
                    team.teamLogoUrl
                )
                navigator.navigate(action)
            }
        }
    }

    private fun initCalendarButton() {
        binding.ivAboutLckCalendar.setOnClickListener {
            toggleDatePickerDialog()
        }

        updateSelectedDate(
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
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
        val year = selectedDate.get(Calendar.YEAR)
        val month = selectedDate.get(Calendar.MONTH)
        val day = selectedDate.get(Calendar.DAY_OF_MONTH)

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
        selectedDate.set(year, month, day)
        updateDateTextView(selectedDate)
        fetchMatchDataForSelectedDate()
    }

    override fun onTeamClick(team: RankingData) {
        val action = AboutLCKFragmentDirections.actionAboutLCKFragmentToAboutLckTeamFragment(team.teamId,team.teamName,team.teamLogoUrl)
        navigator.navigate(action)
    }
}
