package umc.everyones.lck.presentation.lck

import android.annotation.SuppressLint
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
import umc.everyones.lck.presentation.mypage.MyPageActivity
import umc.everyones.lck.util.extension.repeatOnStarted
import umc.everyones.lck.util.extension.setOnSingleClickListener
import umc.everyones.lck.util.extension.showCustomSnackBar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

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
    @SuppressLint("SimpleDateFormat")
    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.matchDetails.collect { result ->
                Log.d("AboutLCKFragment", "Collect")
                handleMatchDetailsResult(result)
            }
        }
        viewLifecycleOwner.repeatOnStarted {
            viewModel.rankingDetails.collect { result ->
                handleRankingDetailsResult(result)
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.temp.collect{
                val temp = it.matchDetailList.groupBy { it.matchDate }
                val temp1 = temp.map {Test(it.key, it.value)
                }.sortedBy { it.date }
                Log.d("temp1", temp1.toString())
                matchVPAdapter.submitList(temp1)
                scrollWithDate("")
            }
        }
    }

    override fun initView() {
        viewModel.fetch()
        initMatchViewPager()
        initRankingRecyclerView()
        initBackButton()
        initCalendarButton()
        goMyPage()
    }

    @SuppressLint("SimpleDateFormat")
    private fun scrollWithDate(inputDate: String){
        Log.d("inputDate", inputDate)
        if(matchVPAdapter.currentList.isEmpty()){
            return
        }
        val date = inputDate.replace(".","-").ifEmpty {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val currentDate = Date(System.currentTimeMillis())
            dateFormat.format(currentDate)
        }
        val index = matchVPAdapter.currentList.indexOfFirst { it.date == date }
        if(index == -1){
            showCustomSnackBar(binding.root, "해당 날짜에 예정된 경기가 없습니다")
        } else {
            binding.vpAboutLckMatch.setCurrentItem(matchVPAdapter.currentList.indexOfFirst { it.date == date }, false)
            binding.tvAboutLckDate.text = date.replace("-",".")
        }
    }

    private fun handleMatchDetailsResult(result: Result<AboutLckMatchDetailsModel>?) {
        result?.onSuccess { data ->
            val matchDataList = matchVPAdapter.getMatchDataList().toMutableList()

            val mappedMatches = data.matchDetailList.map { match ->
                val matchData = createMatchData(match)
                matchData
            }
            matchDataList.addAll(mappedMatches)
            //updateMatchViewPager(matchDataList)
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
            isTeam2Winner = match.team2.winner,
            isMatchFinished = match.matchFinished
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun initMatchViewPager() {
        val viewPager: ViewPager2 = binding.vpAboutLckMatch
        matchVPAdapter = MatchVPAdapter()
        viewPager.adapter = matchVPAdapter

        binding.vpAboutLckMatch.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.tvAboutLckDate.text = matchVPAdapter.currentList[position].date.replace("-",".")
                Log.d("selected dates vp", matchVPAdapter.currentList[position].date)
            }
        })

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
        val formattedDate = String.format("%d.%02d.%02d", selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH) + 1, selectedDate.get(Calendar.DAY_OF_MONTH))
        scrollWithDate(formattedDate)
    }

    override fun onTeamClick(team: RankingData) {
        val action = AboutLCKFragmentDirections.actionAboutLCKFragmentToAboutLckTeamFragment(team.teamId,team.teamName,team.teamLogoUrl)
        navigator.navigate(action)
    }

    data class Test(
        val date: String,
        val list: List<AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel>
    )

    private fun goMyPage(){
        binding.ivMyPage.setOnSingleClickListener {
            startActivity(MyPageActivity.newIntent(requireContext()))
        }
    }
}
