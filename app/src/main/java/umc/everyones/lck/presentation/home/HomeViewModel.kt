package umc.everyones.lck.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.repository.TestRepository
import umc.everyones.lck.domain.repository.home.HomeRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor( // @Inject : 의존성 주입을 받겠다.
    private val repository: HomeRepository
) : ViewModel() {
    private val _navigateEvent = MutableSharedFlow<Int>()
    val navigateEvent: SharedFlow<Int> get() = _navigateEvent

    fun setNavigateEvent(navigateEvent: Int) {
        viewModelScope.launch {
            _navigateEvent.emit(navigateEvent)
        }
    }
    fun fetchHomeTodayMatchInformation(){
        viewModelScope.launch {
            repository.fetchHomeTodayMatchInformation().onSuccess {response ->
                Log.d("fetchHomeTodayMatchInformation", response.toString())
            }.onFailure {
                Log.d("fetchHomeTodayMatchInformation", it.stackTraceToString())
            }
        }
    }
}