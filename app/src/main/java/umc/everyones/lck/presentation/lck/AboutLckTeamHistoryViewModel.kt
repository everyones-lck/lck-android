package umc.everyones.lck.presentation.lck

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.repository.about_lck.AboutLckRepository
import javax.inject.Inject

@HiltViewModel
class AboutLckTeamHistoryViewModel @Inject constructor(
    private val repository: AboutLckRepository
) : ViewModel() {

    fun fetchLckWinningHistory(teamId: Int, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.fetchLckWinningHistory(teamId, page, size)

            result.onSuccess {
                Log.d("AboutLckTeamHistoryViewModel", "fetchLckWinningHistory API 호출 성공")
            }.onFailure {
                Log.e("AboutLckTeamHistoryViewModel", "fetchLckWinningHistory API 호출 실패: ${it.message}")
            }
        }
    }

    fun fetchLckRecentPerformances(teamId: Int, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.fetchLckRecentPerformances(teamId, page, size)

            result.onSuccess {
                Log.d("AboutLckTeamHistoryViewModel", "fetchLckRecentPerformances API 호출 성공")
            }.onFailure {
                Log.e("AboutLckTeamHistoryViewModel", "fetchLckRecentPerformances API 호출 실패: ${it.message}")
            }
        }
    }

    fun fetchLckHistoryOfRoaster(teamId: Int, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.fetchLckHistoryOfRoaster(teamId, page, size)

            result.onSuccess {
                Log.d("AboutLckTeamHistoryViewModel", "fetchLckHistoryOfRoaster API 호출 성공")
            }.onFailure {
                Log.e("AboutLckTeamHistoryViewModel", "fetchLckHistoryOfRoaster API 호출 실패: ${it.message}")
            }
        }
    }
}