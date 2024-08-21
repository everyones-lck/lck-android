package umc.everyones.lck.presentation.lck

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.repository.about_lck.AboutLckRepository
import javax.inject.Inject

@HiltViewModel
class AboutLckTeamHistoryViewModel @Inject constructor(
    private val repository: AboutLckRepository
) : ViewModel() {

    private val _winningHistory = MutableStateFlow<List<String>>(emptyList())
    val winningHistory: StateFlow<List<String>> get() = _winningHistory

    private val _recentPerformances = MutableStateFlow<List<String>>(emptyList())
    val recentPerformances: StateFlow<List<String>> get() = _recentPerformances

    private val _historyOfRoaster = MutableStateFlow<List<String>>(emptyList())
    val historyOfRoaster: StateFlow<List<String>> get() = _historyOfRoaster

    fun fetchLckWinningHistory(teamId: Int, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.fetchLckWinningHistory(teamId, page, size)

            result.onSuccess { response ->
                Log.d("AboutLckTeamHistoryViewModel", "fetchLckWinningHistory API 호출 성공")
                val seasonNameList = response.seasonNameList
                _winningHistory.update { seasonNameList }
            }.onFailure {
                Log.e("AboutLckTeamHistoryViewModel", "fetchLckWinningHistory API 호출 실패: ${it.message}")
            }
        }
    }

    fun fetchLckRecentPerformances(teamId: Int, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.fetchLckRecentPerformances(teamId, page, size)
            result.onSuccess { response ->
                Log.d("AboutLckTeamHistoryViewModel", "fetchLckRecentPerformances API 호출 성공")
                val formattedPerformances = response.seasonDetailList.map { detail ->
                    "${detail.seasonName} - ${formatRanking(detail.rating)}"
                }
                _recentPerformances.update { formattedPerformances }
            }.onFailure {
                Log.e("AboutLckTeamHistoryViewModel", "fetchLckRecentPerformances API 호출 실패: ${it.message}")
            }
        }
    }

    fun fetchLckHistoryOfRoaster(teamId: Int, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.fetchLckHistoryOfRoaster(teamId, page, size)

            result.onSuccess { response ->
                Log.d("AboutLckTeamHistoryViewModel", "fetchLckHistoryOfRoaster API 호출 성공")
                val historyOfRoaster = response.seasonDetails
                    .filter { it.players.isNotEmpty() }
                    .map { seasonDetail ->
                        val playersByPosition = listOf("TOP", "JUNGLE", "MID", "BOT", "SUPPORT").map { position ->
                            seasonDetail.players.find { it.playerPosition?.name == position }?.playerName ?: "000"
                        }.joinToString(" | ")
                        "${seasonDetail.seasonName} $playersByPosition"
                    }
                _historyOfRoaster.update { historyOfRoaster }
            }.onFailure {
                Log.e("AboutLckTeamHistoryViewModel", "fetchLckHistoryOfRoaster API 호출 실패: ${it.message}")
            }
        }
    }

    private fun formatRanking(rating: Int): String {
        return when (rating) {
            1 -> "${rating}st"
            2 -> "${rating}nd"
            3 -> "${rating}rd"
            else -> "${rating}th"
        }
    }
}