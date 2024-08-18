package umc.everyones.lck.presentation.match

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import umc.everyones.lck.domain.repository.match.TodayMatchRepository
import javax.inject.Inject

@HiltViewModel
class TodayMatchViewModel @Inject constructor( // @Inject : 의존성 주입을 받겠다.
) : ViewModel() {
    private val _matchNavigateEvent = MutableSharedFlow<Int>()
    val matchNavigateEvent: SharedFlow<Int> get() = _matchNavigateEvent

    fun setMatchNavigateEvent(matchNavigateEvent: Int) {
        viewModelScope.launch {
            _matchNavigateEvent.emit(matchNavigateEvent)
        }
    }

}