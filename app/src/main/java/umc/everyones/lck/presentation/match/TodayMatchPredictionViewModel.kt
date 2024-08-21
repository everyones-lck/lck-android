package umc.everyones.lck.presentation.match

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.request.match.VoteMatchModel
import umc.everyones.lck.domain.model.request.match.VoteMatchPogModel
import umc.everyones.lck.domain.model.response.match.CommonVotePogModel
import umc.everyones.lck.domain.model.response.match.MatchTodayMatchModel
import umc.everyones.lck.domain.repository.match.TodayMatchRepository
import javax.inject.Inject

@HiltViewModel
class TodayMatchPredictionViewModel @Inject constructor(
    private val repository: TodayMatchRepository
): ViewModel() {

    private val _matchData = MutableLiveData<MatchTodayMatchModel>()
    val matchData: LiveData<MatchTodayMatchModel> get() = _matchData
    private val _voteResponse = MutableLiveData<String>()
    val voteResponse: LiveData<String> get() = _voteResponse

    // 투표 완료 여부를 저장하는 변수
    private val _isVoteCompleted = MutableLiveData<Boolean>(false)
    val isVoteCompleted: LiveData<Boolean> get() = _isVoteCompleted

    fun fetchTodayMatchVoteMatch(matchId: Long) {
        viewModelScope.launch {
            repository.fetchTodayMatchVoteMatch(matchId).onSuccess { response ->
                Log.d("fetchTodayMatchVoteMatch", response.toString())
                _matchData.value = response
            }.onFailure {
                Log.d("fetchTodayMatchVoteMatch", it.stackTraceToString())
            }
        }
    }
    fun voteMatch(matchId: Long, teamId: Int) {
        viewModelScope.launch {
            repository.voteMatch(VoteMatchModel(matchId, teamId)).onSuccess { response ->
                Log.d("voteMatch", response.toString())
                _voteResponse.value = response.message // API에서 전달된 메시지를 ViewModel에 저장
                _isVoteCompleted.value = true  // 투표 완료 상태로 변경

            }.onFailure { response ->
                Log.d("voteMatch", response.stackTraceToString())
                _voteResponse.value = response.message
            }
        }
    }
}