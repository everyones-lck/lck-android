package umc.everyones.lck.presentation.match

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import umc.everyones.lck.domain.model.request.match.VoteMatchPogModel
import umc.everyones.lck.domain.model.request.match.VoteSetPogModel
import umc.everyones.lck.domain.model.response.match.PogPlayerTodayMatchModel
import umc.everyones.lck.domain.model.response.match.TodayMatchSetCountModel
import umc.everyones.lck.domain.repository.match.TodayMatchRepository
import javax.inject.Inject

@HiltViewModel
class TodayMatchTodayPogViewModel @Inject constructor(
    private val repository: TodayMatchRepository
): ViewModel() {
    // 특정 setIndex에 해당하는 POG 데이터를 저장하고, Fragment에 알림
    private val _pogDataMapLiveData = MutableLiveData<Map<Int, PogPlayerTodayMatchModel>>()
    private val pogDataMap = mutableMapOf<Int, PogPlayerTodayMatchModel>()
    val pogDataMapLiveData: LiveData<Map<Int, PogPlayerTodayMatchModel>> get() = _pogDataMapLiveData

    // Match POG 데이터를 저장하는 LiveData
    private val _matchPogData = MutableLiveData<PogPlayerTodayMatchModel>()
    val matchPogData: LiveData<PogPlayerTodayMatchModel> get() = _matchPogData

    // 세트 수를 저장하는 LiveData
    private val _setCount = MutableLiveData<TodayMatchSetCountModel>()
    val setCount: LiveData<TodayMatchSetCountModel> get() = _setCount

    // 각 세트와 매치 POG의 선택된 플레이어 ID를 저장하는 LiveData
    private val _selectedSetPlayers = MutableLiveData<Map<Int, Int>>() // key: setIndex, value: playerId
    val selectedSetPlayers: LiveData<Map<Int, Int>> get() = _selectedSetPlayers

    private val _selectedMatchPlayer = MutableLiveData<Int?>()
    val selectedMatchPlayer: LiveData<Int?> get() = _selectedMatchPlayer

    // 투표 결과 메시지를 저장하는 LiveData
    private val _voteResponse = MutableLiveData<String>()
    val voteResponse: LiveData<String> get() = _voteResponse

    // 모든 아이템이 선택되었는지 여부를 저장하는 LiveData
    private val _allItemsSelected = MutableLiveData<Boolean>()
    val allItemsSelected: LiveData<Boolean> get() = _allItemsSelected

    // 에러 메시지를 저장하는 LiveData
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage
    fun clearErrorMessage() {
        _errorMessage.value = null
    }


    // 세트 POG의 플레이어를 선택하는 함수
    fun selectSetPlayer(setIndex: Int, playerId: Int) {
        val updatedSetPlayers = _selectedSetPlayers.value?.toMutableMap() ?: mutableMapOf()
        updatedSetPlayers[setIndex] = playerId
        _selectedSetPlayers.value = updatedSetPlayers
        checkIfAllItemsSelected()
    }
    // 매치 POG의 플레이어를 선택하는 함수
    fun selectMatchPlayer(playerId: Int) {
        _selectedMatchPlayer.value = playerId
        checkIfAllItemsSelected()
    }

    // 모든 세트와 매치 POG에서 플레이어가 선택되었는지 확인하는 함수
    private fun checkIfAllItemsSelected() {
        val allSetsSelected = _selectedSetPlayers.value?.size == _setCount.value?.setCount
        val isMatchSelected = _selectedMatchPlayer.value != null
        _allItemsSelected.value = allSetsSelected && isMatchSelected
    }

    // 예외로부터 에러 메시지를 추출하는 함수
    private fun getErrorMessageFromException(exception: Throwable): String {
        return if (exception is HttpException) {
            val errorBody = exception.response()?.errorBody()?.string()
            parseErrorMessage(errorBody) ?: "이미 투표한 경기입니다."
        } else {
            "투표 되었습니다!"
        }
    }

    // 서버에서 반환한 에러 메시지를 파싱하는 함수
    private fun parseErrorMessage(errorBody: String?): String? {
        return errorBody?.let {
            // 서버 응답 구조에 맞게 파싱
            try {
                val jsonObject = JSONObject(it)
                jsonObject.optString("message")
            } catch (e: JSONException) {
                null
            }
        }
    }

    // 세트별 POG 데이터를 불러오는 함수
    fun fetchTodayMatchVoteSetPog(matchId: Long, setIndex: Int) {
        viewModelScope.launch {
            repository.fetchTodayMatchVoteSetPog(matchId, setIndex).onSuccess { response ->
                pogDataMap[setIndex] = response
                // Map을 LiveData로 업데이트
                _pogDataMapLiveData.value = pogDataMap.toMap()
            }.onFailure {
                Timber.d("fetchTodayMatchVoteSetPog", it.stackTraceToString())
            }
        }
    }
    // 매치별 POG 데이터를 불러오는 함수
    fun fetchTodayMatchVoteMatchPog(matchId: Long) {
        viewModelScope.launch {
            repository.fetchTodayMatchVoteMatchPog(matchId).onSuccess { response ->
                Timber.d("fetchTodayMatchVoteMatchPog", response.toString())
                _matchPogData.value = response
            }.onFailure {
                Timber.d("fetchTodayMatchVoteMatchPog", it.stackTraceToString())
            }
        }
    }
    // 세트 POG에 투표하는 함수
    fun voteSetPog(matchId: Long, setIndex: Int, playerId: Int) {
        viewModelScope.launch {
            repository.voteSetPog(VoteSetPogModel(matchId, setIndex, playerId)).onSuccess { response ->
                Timber.d("voteSetPog", response.toString())
                _voteResponse.value = "투표 되었습니다!"
            }.onFailure { exception ->
                Timber.d("voteSetPog", exception.stackTraceToString())
                _voteResponse.value = getErrorMessageFromException(exception)
            }
        }
    }
    // 매치 POG에 투표하는 함수
    fun voteMatchPog(matchId: Long, playerId: Int) {
        viewModelScope.launch {
            repository.voteMatchPog(VoteMatchPogModel(matchId, playerId)).onSuccess { response ->
                Timber.d("voteMatchPog", response.toString())
                _voteResponse.value = "투표 되었습니다!"
            }.onFailure { exception ->
                Timber.d("voteMatchPog", exception.stackTraceToString())
                _voteResponse.value = getErrorMessageFromException(exception)
            }
        }
    }
    // 세트 수를 불러오는 함수
    fun fetchTodayMatchSetCount(matchId: Long) {
        viewModelScope.launch {
            repository.fetchTodayMatchSetCount(matchId).onSuccess { response ->
                Timber.d("fetchTodayMatchSetCount", response.toString())
                _setCount.value = response
            }.onFailure {
                Timber.d("fetchTodayMatchSetCount", it.stackTraceToString())
            }
        }
    }
}