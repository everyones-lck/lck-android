package umc.everyones.lck.presentation.party.chat

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
import umc.everyones.lck.domain.model.response.party.ViewingPartyChatLogModel
import umc.everyones.lck.domain.model.response.party.ViewingPartyChatRoomModel
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository
import umc.everyones.lck.util.network.EventFlow
import umc.everyones.lck.util.network.MutableEventFlow
import umc.everyones.lck.util.network.UiState

@HiltViewModel
class ViewingPartyChatViewModel @Inject constructor(
    private val repository: ViewingPartyRepository
) : ViewModel() {
    private val _postId = MutableStateFlow<Long>(-1)
    val postId: StateFlow<Long> get() = _postId
    
    private val _roomId = MutableStateFlow<Long>(0L)
    val roomId: StateFlow<Long> get() = _roomId

    private val _viewingPartyChatEvent = MutableStateFlow<UiState<ViewingPartyChatEvent>>(UiState.Empty)
    val viewingPartyChatEvent : StateFlow<UiState<ViewingPartyChatEvent>> get() = _viewingPartyChatEvent

    sealed class ViewingPartyChatEvent {
        data class CreateChatRoom(val result: ViewingPartyChatRoomModel): ViewingPartyChatEvent()
        data class FetchChatLog(val chatLog: ViewingPartyChatLogModel): ViewingPartyChatEvent()
    }


    fun setPostId(postId: Long){
        _postId.value = postId
    }
    fun createViewingPartyChatRoom(){
        viewModelScope.launch {
            _viewingPartyChatEvent.value = UiState.Loading
            repository.createViewingPartyChatRoom(postId.value).onSuccess { response ->
                _viewingPartyChatEvent.value = UiState.Success(ViewingPartyChatEvent.CreateChatRoom(response))
                Log.d("createViewingPartyChatRoom", response.toString())
                _roomId.value = response.roomId
            }.onFailure {
                Log.d("createViewingPartyChatRoom error", it.stackTraceToString())
            }
        }
    }

    fun createViewingPartyChatRoomAsParticipant(){
        viewModelScope.launch {
            _viewingPartyChatEvent.value = UiState.Loading
            repository.createViewingPartyChatRoomAsParticipant(postId.value).onSuccess { response ->
                _viewingPartyChatEvent.value = UiState.Success(ViewingPartyChatEvent.CreateChatRoom(response))
                Log.d("createViewingPartyChatRoomAsParticipant", response.toString())
                _roomId.value = response.roomId
            }.onFailure {
                Log.d("createViewingPartyChatRoomAsParticipant error", it.stackTraceToString())
            }
        }
    }

    fun fetchViewingPartyChatLog(roomId: Long){
        viewModelScope.launch{
            _viewingPartyChatEvent.value = UiState.Loading
            repository.fetchViewingPartyChatLog(roomId, 0, 10).onSuccess { response ->
                Log.d("fetchViewingPartyChatLog", response.toString())
                _viewingPartyChatEvent.value = UiState.Success(ViewingPartyChatEvent.FetchChatLog(response))
            }.onFailure {
                Log.d("createViewingPartyChatRoom error", it.stackTraceToString())
            }
        }
    }
}