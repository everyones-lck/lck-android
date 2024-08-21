package umc.everyones.lck.presentation.lck

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.about_lck.AboutLckMatchDetailsModel
import umc.everyones.lck.domain.model.about_lck.AboutLckRankingDetailsModel
import umc.everyones.lck.domain.repository.about_lck.AboutLckRepository
import umc.everyones.lck.util.network.NetworkResult
import umc.everyones.lck.util.network.onSuccess
import java.time.LocalDate
import java.time.LocalDate.parse

import javax.inject.Inject


@HiltViewModel
class AboutLckViewModel @Inject constructor(
    private val repository: AboutLckRepository
) : ViewModel() {

    private val _matchDetails = MutableStateFlow<Result<AboutLckMatchDetailsModel>?>(null)
    val matchDetails: StateFlow<Result<AboutLckMatchDetailsModel>?> get() = _matchDetails
    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> get() = _title

    private val _rankingDetails = MutableStateFlow<Result<AboutLckRankingDetailsModel>?>(null)
    val rankingDetails: StateFlow<Result<AboutLckRankingDetailsModel>?> get() = _rankingDetails


    fun fetchLckMatchDetails(searchDate: String) {
        viewModelScope.launch {
            try {
                val startDate = parse(searchDate).minusDays(1)
                val endDate = parse(searchDate).plusDays(1)

                val matchDetailsList = mutableListOf<AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel>()

                val dateRange = generateSequence(startDate) { it.plusDays(1) }
                    .takeWhile { it <= endDate }

                Log.d("ViewModel", "Fetching matches for dates: ${dateRange.joinToString()}")

                dateRange.forEach { date ->
                    Log.d("ViewModel", "Fetching match details for date: $date")
                    val result = repository.fetchLckMatchDetails(date.toString())
                    result.onSuccess { data ->
                        Log.d("ViewModel", "Fetched ${data.matchDetailList.size} matches for date: $date")
                        matchDetailsList.addAll(data.matchDetailList)
                    }.onFailure { error ->
                        Log.e("ViewModel", "Failed to fetch matches for date $date: ${error.message}")
                    }
                }

                val matchDetailsModel = AboutLckMatchDetailsModel(matchDetailsList, matchDetailsList.size)
                _matchDetails.value = Result.success(matchDetailsModel)

                _title.value = if (matchDetailsList.isNotEmpty()) {
                    formatMatchTitle(matchDetailsList.first().season, matchDetailsList.first().matchNumber)
                } else {
                    "No Matches"
                }

                Log.d("ViewModel", "Final match details size: ${matchDetailsList.size}")

            } catch (e: Exception) {
                Log.e("ViewModel", "Exception during match details fetch: ${e.message}")
                _matchDetails.value = Result.failure(e)
                _title.value = "Failed to load matches"
            }
        }
    }


    fun fetchLckRanking(seasonName: String, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.fetchLckRanking(seasonName, page, size)

            result.onSuccess { response ->
                _rankingDetails.value = Result.success(response)
            }.onFailure { exception ->
                _rankingDetails.value = Result.failure(exception)
                Log.e("AboutLckViewModel", "fetchLckRanking API 호출 실패: ${exception.message}")
            }
        }
    }


    fun formatMatchTitle(season: String, matchNumber: Int): String {
        val suffix = when (matchNumber % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
        return "$season LCK ${matchNumber}${suffix} Match"
    }

    fun getWinningTeamName(match: AboutLckMatchDetailsModel.AboutLckMatchDetailsElementModel): String {
        return if (match.team1.winner) {
            match.team1.teamName
        } else {
            match.team2.teamName
        }
    }
}

