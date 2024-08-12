package umc.everyones.lck.presentation.party.chat

import android.util.Log
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository

@HiltViewModel
class ViewingPartyChatViewModel @Inject constructor(
    private val repository: ViewingPartyRepository
) : ViewModel() {
    private val _postId = MutableStateFlow<Long>(-1)
    val postId: StateFlow<Long> get() = _postId
    
    private val _roomId = MutableStateFlow<Long>(4)
    val roomId: StateFlow<Long> get() = _roomId


    fun setPostId(postId: Long){
        _postId.value = postId
    }
    fun createViewingPartyChatRoom(){
        viewModelScope.launch {
            repository.createViewingPartyChatRoom(postId.value).onSuccess { response ->
                Log.d("createViewingPartyChatRoom", response.toString())
                _roomId.value = response.roomId
            }.onFailure {
                Log.d("createViewingPartyChatRoom error", it.stackTraceToString())
            }
        }
    }

    fun fetchViewingPartyChatLog(){
        viewModelScope.launch{
            repository.fetchViewingPartyChatLog(roomId.value, 0, 10).onSuccess { response ->
                Log.d("fetchViewingPartyChatLog", response.toString())
            }.onFailure {
                Log.d("createViewingPartyChatRoom error", it.stackTraceToString())
            }
        }
    }
}