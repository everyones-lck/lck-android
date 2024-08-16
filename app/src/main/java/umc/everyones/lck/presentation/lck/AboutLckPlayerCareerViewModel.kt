package umc.everyones.lck.presentation.lck

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.repository.about_lck.AboutLckRepository
import javax.inject.Inject

@HiltViewModel
class AboutLckPlayerCareerViewModel @Inject constructor(
    private val repository: AboutLckRepository
) : ViewModel() {

    fun fetchLckWinningCareer(playerId: Int, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.fetchLckWinningCareer(playerId, page, size)

            result.onSuccess {
                Log.d("AboutLckPlayerCareerViewModel", "fetchLckWinningCareer API 호출 성공")
            }.onFailure {
                Log.e("AboutLckPlayerCareerViewModel", "fetchLckWinningCareer API 호출 실패: ${it.message}")
            }
        }
    }

    fun fetchLckHistory(playerId: Int, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.fetchLckHistory(playerId, page, size)

            result.onSuccess {
                Log.d("AboutLckPlayerCareerViewModel", "fetchLckHistory API 호출 성공")
            }.onFailure {
                Log.e("AboutLckPlayerCareerViewModel", "fetchLckHistory API 호출 실패: ${it.message}")
            }
        }
    }

    fun fetchLckPlayer(playerId: Int) {
        viewModelScope.launch {
            val result = repository.fetchLckPlayer(playerId)

            result.onSuccess {
                Log.d("AboutLckPlayerCareerViewModel", "fetchLckPlayer API 호출 성공")
            }.onFailure {
                Log.e("AboutLckPlayerCareerViewModel", "fetchLckPlayer API 호출 실패: ${it.message}")
            }
        }
    }
}