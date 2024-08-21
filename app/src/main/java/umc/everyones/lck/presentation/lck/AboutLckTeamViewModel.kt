package umc.everyones.lck.presentation.lck

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import umc.everyones.lck.data.dto.response.about_lck.LckPlayerDetailsResponseDto
import umc.everyones.lck.domain.model.about_lck.AboutLckPlayerDetailsModel
import umc.everyones.lck.domain.repository.about_lck.AboutLckRepository
import javax.inject.Inject

@HiltViewModel
class AboutLckTeamViewModel @Inject constructor(
    private val repository: AboutLckRepository
) : ViewModel() {

    private val _teamId = MutableStateFlow<Int?>(null)
    val teamId: StateFlow<Int?> get() = _teamId

    private val _playerDetails = MutableStateFlow<Result<AboutLckPlayerDetailsModel>?>(null)
    val playerDetails: StateFlow<Result<AboutLckPlayerDetailsModel>?> get() = _playerDetails

    fun fetchLckPlayerDetails(teamId: Int, seasonName: String, player_role: LckPlayerDetailsResponseDto.PlayerRole) {
        viewModelScope.launch {
            val result = repository.fetchLckPlayerDetails(teamId, seasonName, player_role)
            _playerDetails.value = result

            result.onSuccess { response ->
                Log.d("AboutLckTeamViewModel", "fetchLckPlayerDetails API 호출 성공")
                val filteredPlayerDetails = response.copy(
                    playerDetails = response.playerDetails.map { playerDetail ->
                        playerDetail.copy(playerRole = null)
                    }
                )
                _playerDetails.value = Result.success(filteredPlayerDetails)
            }.onFailure {
                Log.e("AboutLckTeamViewModel", "fetchLckPlayerDetails API 호출 실패: ${it.message}")
                _playerDetails.value = Result.failure(it)
            }
        }
    }
    fun setTeamId(id: Int) {
        _teamId.value = id
    }

}