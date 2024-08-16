package umc.everyones.lck.presentation.lck

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel
import umc.everyones.lck.domain.repository.about_lck.AboutLckRepository
import javax.inject.Inject

@HiltViewModel
class AboutLckTeamViewModel @Inject constructor(
    private val repository: AboutLckRepository
) : ViewModel() {

    fun fetchLckPlayerDetails(teamId: Int, seasonName: String, player_role: AboutLckPlayerDetailsModel.PlayerRole) {
        viewModelScope.launch {
            val result = repository.fetchLckPlayerDetails(teamId, seasonName, player_role)

            result.onSuccess {
                Log.d("AboutLckTeamViewModel", "fetchLckPlayerDetails API 호출 성공")
            }.onFailure {
                Log.e("AboutLckTeamViewModel", "fetchLckPlayerDetails API 호출 실패: ${it.message}")
            }
        }
    }
}