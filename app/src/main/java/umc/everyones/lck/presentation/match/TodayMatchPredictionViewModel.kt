package umc.everyones.lck.presentation.match

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.request.match.VoteMatchModel
import umc.everyones.lck.domain.model.request.match.VoteMatchPogModel
import umc.everyones.lck.domain.repository.match.TodayMatchRepository
import javax.inject.Inject

@HiltViewModel
class TodayMatchPredictionViewModel @Inject constructor(
    private val repository: TodayMatchRepository
): ViewModel() {
    fun fetchTodayMatchVoteMatch() {
        viewModelScope.launch {
            repository.fetchTodayMatchVoteMatch(89).onSuccess { response ->
                Log.d("fetchTodayMatchVoteMatch", response.toString())
            }.onFailure {
                Log.d("fetchTodayMatchVoteMatch", it.stackTraceToString())
            }
        }
    }
    fun voteMatch() {
        viewModelScope.launch {
            repository.voteMatch(VoteMatchModel(89, 4)).onSuccess { response ->
                Log.d("voteMatch", response.toString())
            }.onFailure {
                Log.d("voteMatch", it.stackTraceToString())
            }
        }
    }
}