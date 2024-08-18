package umc.everyones.lck.presentation.match

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.request.match.CommonPogModel
import umc.everyones.lck.domain.repository.match.TodayMatchRepository
import javax.inject.Inject

@HiltViewModel
class TodayMatchLckPogViewModel @Inject constructor(
    private val repository: TodayMatchRepository
): ViewModel() {
    fun voteTodayMatchSetPog() {
        viewModelScope.launch {
            repository.voteTodayMatchSetPog(1, CommonPogModel(89)).onSuccess { response ->
                Log.d("voteTodayMatchSetPog", response.toString())
            }.onFailure {
                Log.d("voteTodayMatchSetPog", it.stackTraceToString())
            }
        }
    }

    fun voteTodayMatchMatchPog() {
        viewModelScope.launch {
            repository.voteTodayMatchMatchPog(CommonPogModel(89)).onSuccess { response ->
                Log.d("voteTodayMatchMatchPog", response.toString())
            }.onFailure {
                Log.d("voteTodayMatchMatchPog", it.stackTraceToString())
            }
        }
    }
}