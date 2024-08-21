package umc.everyones.lck.presentation.lck

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.about_lck.AboutLckMatchDetailsModel
import umc.everyones.lck.domain.model.about_lck.AboutLckRankingDetailsModel
import umc.everyones.lck.domain.repository.about_lck.AboutLckRepository

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

    val temp = MutableSharedFlow<AboutLckMatchDetailsModel>()

    fun fetch(){
        viewModelScope.launch {
            repository.fetchLckMatchDetails("2024-08-21").onSuccess {
                temp.emit(it)
            }.onFailure {

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

