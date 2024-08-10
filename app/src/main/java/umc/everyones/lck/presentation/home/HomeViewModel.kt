package umc.everyones.lck.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.repository.TestRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor( // @Inject : 의존성 주입을 받겠다.
) : ViewModel() {
    private val _navigateEvent = MutableSharedFlow<Int>()
    val navigateEvent: SharedFlow<Int> get() = _navigateEvent

    fun setNavigateEvent(navigateEvent: Int) {
        viewModelScope.launch {
            _navigateEvent.emit(navigateEvent)
        }
    }
}