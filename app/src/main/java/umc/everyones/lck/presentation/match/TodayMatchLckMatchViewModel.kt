package umc.everyones.lck.presentation.match

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.repository.match.TodayMatchRepository
import javax.inject.Inject

@HiltViewModel
class TodayMatchLckMatchViewModel @Inject constructor(
    private val repository: TodayMatchRepository
): ViewModel() {
    fun fetchTodayMatchInformation(){
        viewModelScope.launch {
            repository.fetchTodayMatchInformation().onSuccess {response ->
                Log.d("fetchTodayMatchInformation", response.toString())
            }.onFailure {
                Log.d("fetchTodayMatchInformation", it.stackTraceToString())
            }
        }
    }
}