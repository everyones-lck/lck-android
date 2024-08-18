package umc.everyones.lck.presentation.match

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.request.match.CommonPogModel
import umc.everyones.lck.domain.model.request.match.VoteMatchPogModel
import umc.everyones.lck.domain.model.request.match.VoteSetPogModel
import umc.everyones.lck.domain.repository.match.TodayMatchRepository
import javax.inject.Inject

@HiltViewModel
class TodayMatchTodayPogViewModel @Inject constructor(
    private val repository: TodayMatchRepository
): ViewModel() {
    fun fetchTodayMatchVoteSetPog() {
        viewModelScope.launch {
            repository.fetchTodayMatchVoteSetPog(89, 1).onSuccess { response ->
                Log.d("fetchTodayMatchVoteSetPog", response.toString())
            }.onFailure {
                Log.d("fetchTodayMatchVoteSetPog", it.stackTraceToString())
            }
        }
    }
    fun fetchTodayMatchVoteMatchPog() {
        viewModelScope.launch {
            repository.fetchTodayMatchVoteMatchPog(89).onSuccess { response ->
                Log.d("fetchTodayMatchVoteMatchPog", response.toString())
            }.onFailure {
                Log.d("fetchTodayMatchVoteMatchPog", it.stackTraceToString())
            }
        }
    }
    fun voteSetPog() {
        viewModelScope.launch {
            repository.voteSetPog(VoteSetPogModel(89, 1, 15)).onSuccess { response ->
                Log.d("voteSetPog", response.toString())
            }.onFailure {
                Log.d("voteSetPog", it.stackTraceToString())
            }
        }
    }
    fun voteMatchPog() {
        viewModelScope.launch {
            repository.voteMatchPog(VoteMatchPogModel(89, 15)).onSuccess { response ->
                Log.d("voteMatchPog", response.toString())
            }.onFailure {
                Log.d("voteMatchPog", it.stackTraceToString())
            }
        }
    }
}