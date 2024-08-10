package umc.everyones.lck.presentation.party.read

import android.util.Log
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.party.ReadViewingPartyModel
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository

@HiltViewModel
class ReadViewingPartyViewModel @Inject constructor(
    private val repository: ViewingPartyRepository
) : ViewModel() {
    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> get() = _title

    private val _postId = MutableStateFlow<Long>(-1)
    val postId: StateFlow<Long> get() = _postId

    private val _readViewingPartyEvent = MutableSharedFlow<ReadViewingPartyEvent>()
    val readViewingPartyEvent: SharedFlow<ReadViewingPartyEvent> get() = _readViewingPartyEvent
    sealed class ReadViewingPartyEvent {
        data class Read(val viewingParty: ReadViewingPartyModel): ReadViewingPartyEvent()
        data class Success(val message: String): ReadViewingPartyEvent()

        data class Fail(val message: String): ReadViewingPartyEvent()
    }

    fun eventReadViewingParty(event: ReadViewingPartyEvent){
        viewModelScope.launch {
            _readViewingPartyEvent.emit(event)
        }
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setPostId(postId: Long){
        _postId.value = postId
    }

    fun fetchViewingParty() {
        viewModelScope.launch {
            repository.fetchViewingParty(postId.value).onSuccess { response ->
                Log.d("fetchViewingParty", response.toString())
                _readViewingPartyEvent.emit(ReadViewingPartyEvent.Read(response))
            }.onFailure {
                _readViewingPartyEvent.emit(ReadViewingPartyEvent.Fail("뷰잉파티를 조회하지 못했습니다"))
            }
        }
    }

    fun joinViewingParty(){
        viewModelScope.launch{
            repository.joinViewingParty(postId.value).onSuccess { response ->
                Log.d("joinViewingParty", response.toString())
                _readViewingPartyEvent.emit(ReadViewingPartyEvent.Success("뷰잉파티에 참여되었습니다!"))
            }.onFailure {
                _readViewingPartyEvent.emit(ReadViewingPartyEvent.Fail("뷰잉파티에 참여하지 못했습니다"))
            }
        }
    }
}