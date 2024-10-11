package umc.everyones.lck.presentation.lck

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
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
import java.util.Locale

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
            viewModel.matchDetails.collect { matchDetails ->
                if (matchDetails != null) {
                    updateMatchDetails(matchDetails)
                } else {
                    Timber.e("Failed to fetch match details")
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.rankingDetails.collect { rankingDetails ->
                if (rankingDetails != null) {
                    updateRankingDetails(rankingDetails)
                } else {
                    Timber.e("Failed to fetch ranking details")
                }
            }
        }
    }

    override fun initView() {
        fetchMatchDetails(getFormattedDate(selectedDate))
        initMatchViewPager()
        initRankingRecyclerView()
        initBackButton()
        initCalendarButton()
        goMyPage()
    }
    private fun fetchMatchDetails(date: String) {
        Timber.d("API 호출 - 요청 날짜: $date")
        viewModel.fetchLckMatchDetails(date)
    }
    @SuppressLint("SimpleDateFormat")
    private fun updateMatchDetails(matchDetailsModel: AboutLckMatchDetailsModel) {
        val matchDataGroupedByDate = matchDetailsModel.matchByDateList.map { dateModel ->
            AboutLCKFragment.Test(dateModel.matchDate, dateModel.matchDetailList)
        }.sortedBy { it.date }

        val filteredList = filterDuplicateData(matchDataGroupedByDate, getFormattedDate(selectedDate))

        Timber.d("현재 selectedDate: ${getFormattedDate(selectedDate)}")
        Timber.d("매치 데이터 날짜: %s", matchDataGroupedByDate.map { it.date })

        if (filteredList != matchVPAdapter.currentList) {
            matchVPAdapter.submitList(filteredList.toList()) {
                scrollWithDate(getFormattedDate(selectedDate))
            }
        } else {
            Timber.d("리스트가 동일하여 갱신하지 않음")
        }
    }

    private fun filterDuplicateData(newData: List<Test>, selectedDate: String): List<Test> {
        val currentList = matchVPAdapter.currentList

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.time = dateFormat.parse(selectedDate)!!

        val previousDay = Calendar.getInstance().apply { time = selectedCalendar.time; add(Calendar.DATE, -1) }
        val nextDay = Calendar.getInstance().apply { time = selectedCalendar.time; add(Calendar.DATE, 1) }

        val mustIncludeDates = listOf(
            dateFormat.format(previousDay.time),
            selectedDate,
            dateFormat.format(nextDay.time)
        )
        val filteredData = newData.filter { newItem ->
            val isDuplicate = currentList.any { currentItem -> currentItem.date == newItem.date }

            if (isDuplicate && mustIncludeDates.contains(newItem.date)) {
                true
            } else if (!isDuplicate) {
                true
            } else {
                false
            }
        }
        return filteredData
    }

    @SuppressLint("SimpleDateFormat")
    private fun scrollWithDate(inputDate: String) {
        Timber.d("ViewPager 스크롤 - 입력된 날짜: $inputDate")

        val currentIndex = matchVPAdapter.currentList.indexOfFirst { it.date == inputDate }
        Timber.d("찾은 인덱스: $currentIndex")
        Timber.d("현재 ViewPager의 currentItem: ${binding.vpAboutLckMatch.currentItem}")

        if (currentIndex != binding.vpAboutLckMatch.currentItem) {
            binding.vpAboutLckMatch.setCurrentItem(currentIndex, false)
            binding.tvAboutLckDate.text = inputDate.replace("-", ".")
            Timber.d("ViewPager가 포지션 %s로 스크롤됨", currentIndex)
        } else {
            Timber.d("해당 날짜의 매치 데이터를 찾을 수 없습니다: $inputDate")
        }
    }

    private fun getFormattedDate(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    private fun initMatchViewPager() {
        val viewPager: ViewPager2 = binding.vpAboutLckMatch
        matchVPAdapter = MatchVPAdapter()
        viewPager.adapter = matchVPAdapter

        binding.vpAboutLckMatch.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Timber.d("ViewPager 페이지 선택 - 포지션: $position")

                val selectedPageDate = matchVPAdapter.currentList[position].date
                Timber.d("선택된 페이지 날짜: $selectedPageDate")

                if (getFormattedDate(selectedDate) != selectedPageDate) {
                    Timber.d("selectedDate 업데이트 전: ${getFormattedDate(selectedDate)}")
                    updateSelectedPageDate(selectedPageDate)
                    Timber.d("selectedDate 업데이트 후: ${getFormattedDate(selectedDate)}")
                } else {
                    Timber.d("동일한 날짜에 대한 API 호출 방지")
                }
            }
        })
        viewPager.offscreenPageLimit = 2

        val pageMarginPx = 16 * resources.displayMetrics.densityDpi / 160
        val offsetPx = 40 * resources.displayMetrics.densityDpi / 160

        viewPager.setPageTransformer { page, position ->
            page.translationX = -offsetPx * position
        }

        val recyclerViewMatches = viewPager.getChildAt(0) as RecyclerView
        recyclerViewMatches.setPadding(pageMarginPx, 0, pageMarginPx, 0)
        recyclerViewMatches.clipToPadding = false


    }
    private fun updateSelectedPageDate(selectedPageDate: String) {
        val dateParts = selectedPageDate.split("-")
        selectedDate.set(dateParts[0].toInt(), dateParts[1].toInt() - 1, dateParts[2].toInt())
        binding.tvAboutLckDate.text = selectedPageDate.replace("-", ".")
        fetchMatchDetails(selectedPageDate)
    }

    private fun updateRankingDetails(rankingDetails: AboutLckRankingDetailsModel) {
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
        binding.viewAboutLckRect1.setOnSingleClickListener {
            topTeams.getOrNull(0)?.let { team ->
                val action = AboutLCKFragmentDirections.actionAboutLCKFragmentToAboutLckTeamFragment(
                    team.teamId,
                    team.teamName,
                    team.teamLogoUrl
                )
                navigator.navigate(action)
            }
        }

        binding.viewAboutLckRect2.setOnSingleClickListener {
            topTeams.getOrNull(1)?.let { team ->
                val action = AboutLCKFragmentDirections.actionAboutLCKFragmentToAboutLckTeamFragment(
                    team.teamId,
                    team.teamName,
                    team.teamLogoUrl
                )
                navigator.navigate(action)
            }
        }

        binding.viewAboutLckRect3.setOnSingleClickListener {
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
        binding.ivAboutLckCalendar.setOnSingleClickListener {
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
        val formattedDate = getFormattedDate(selectedDate)
        fetchMatchDetails(formattedDate)
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
