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

    fun fetchLckMatchDetails(searchDate: String) {
        viewModelScope.launch {
            val result = repository.fetchLckMatchDetails(searchDate)

            result.onSuccess { response ->
                // API 호출 성공 시 로그로 응답 데이터 출력
                Log.d("AboutLckViewModel", "fetchLckMatchDetails API 호출 성공: $response")
            }.onFailure { exception ->
                // API 호출 실패 시 로그로 에러 메시지 출력
                Log.e("AboutLckViewModel", "fetchLckMatchDetails API 호출 실패: ${exception.message}")
            }
        }
    }

    fun fetchLckRanking(seasonName: String, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.fetchLckRanking(seasonName, page, size)

            result.onSuccess { response ->
                // API 호출 성공 시 로그로 응답 데이터 출력
                Log.d("AboutLckViewModel", "fetchLckRanking API 호출 성공: $response")
            }.onFailure { exception ->
                // API 호출 실패 시 로그로 에러 메시지 출력
                Log.e("AboutLckViewModel", "fetchLckRanking API 호출 실패: ${exception.message}")
            }
        }
    }
}
    /*// StateFlow로 상태 관리
    private val _matchDetails = MutableStateFlow<NetworkResult<AboutLckMatchDetailsModel>?>(null)
    val matchDetails: StateFlow<NetworkResult<AboutLckMatchDetailsModel>?> = _matchDetails.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O) //parse 때문에 필요
    fun fetchLckMatchDetails(searchDate: String) {
        viewModelScope.launch {
            val parsedDate = parse(searchDate)
            val result = repository.fetchLckMatchDetails(parsedDate)
            _matchDetails.value = result
        }
    }*/

