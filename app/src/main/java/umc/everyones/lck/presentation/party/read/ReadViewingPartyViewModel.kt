package umc.everyones.lck.presentation.party.read

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.response.party.ReadViewingPartyModel
import umc.everyones.lck.domain.model.response.party.ViewingPartyParticipantsModel
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository
import umc.everyones.lck.util.network.EventFlow
import umc.everyones.lck.util.network.MutableEventFlow
import umc.everyones.lck.util.network.UiState

@HiltViewModel
class ReadViewingPartyViewModel @Inject constructor(
    private val repository: ViewingPartyRepository,
    private val spf: SharedPreferences
) : ViewModel() {
    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> get() = _title

    private val _postId = MutableStateFlow<Long>(-1)
    val postId: StateFlow<Long> get() = _postId

    private val _readViewingPartyEvent = MutableStateFlow<UiState<ReadViewingPartyEvent>>(UiState.Empty)
    val readViewingPartyEvent: StateFlow<UiState<ReadViewingPartyEvent>> get() = _readViewingPartyEvent

    private val _isWriter = MutableEventFlow<Boolean>()
    val isWriter: EventFlow<Boolean> get() = _isWriter

    private var _viewingPartyParticipantsPage = repository.fetchViewingPartyParticipantsPagingSource(postId.value).cachedIn(viewModelScope)

    val viewingPartyParticipantsPage get() = _viewingPartyParticipantsPage
    sealed class ReadViewingPartyEvent {
        data class ReadViewingParty(val viewingParty: ReadViewingPartyModel): ReadViewingPartyEvent()
        data object JoinViewingParty: ReadViewingPartyEvent()

        data object DeleteViewingParty: ReadViewingPartyEvent()

        data class ReadParticipants(val participants: ViewingPartyParticipantsModel): ReadViewingPartyEvent()

        data class WriteDoneViewingParty(val isWriteDone: Boolean): ReadViewingPartyEvent()
    }
    fun setTitle(title: String) {
        _title.value = title
    }

    fun setPostId(postId: Long){
        _postId.value = postId
        _viewingPartyParticipantsPage = repository.fetchViewingPartyParticipantsPagingSource(postId).cachedIn(viewModelScope)
    }

    fun fetchViewingParty() {
        viewModelScope.launch {
            _readViewingPartyEvent.value = UiState.Loading
            repository.fetchViewingParty(postId.value).onSuccess { response ->
                Log.d("fetchViewingParty", response.toString())
                _readViewingPartyEvent.value = UiState.Success(ReadViewingPartyEvent.ReadViewingParty(response))
                val writerName = response.writerInfo.split(" ").first().trim()
                _isWriter.emit(spf.getString("nickname", "").toString() == writerName)
            }.onFailure {
                Log.d("fetchViewingParty error", it.stackTraceToString())
                _readViewingPartyEvent.value = UiState.Failure("뷰잉파티를 조회하지 못했습니다")
            }
        }
    }

    fun joinViewingParty(){
        viewModelScope.launch{
            _readViewingPartyEvent.value = UiState.Loading
            repository.joinViewingParty(postId.value).onSuccess { response ->
                Log.d("joinViewingParty", response.toString())
                _readViewingPartyEvent.value = UiState.Success(ReadViewingPartyEvent.JoinViewingParty)
            }.onFailure {
                Log.d("joinViewingParty error", it.stackTraceToString())
                _readViewingPartyEvent.value = UiState.Failure("뷰잉파티에 참여하지 못했습니다")
            }
        }
    }

    fun deleteViewingParty(){
        viewModelScope.launch {
            _readViewingPartyEvent.value = UiState.Loading
            repository.deleteViewingParty(postId.value).onSuccess { response ->
                Log.d("deleteViewingParty", response.toString())
                _readViewingPartyEvent.value = UiState.Success(ReadViewingPartyEvent.DeleteViewingParty)
            }.onFailure {
                Log.d("deleteViewingParty error", it.stackTraceToString())
                _readViewingPartyEvent.value = UiState.Failure("뷰잉파티를 삭제하지 못했습니다")
            }
        }
    }

    fun fetchViewingPartyParticipants(){
        viewModelScope.launch {
            _readViewingPartyEvent.value = UiState.Loading
            repository.fetchViewingPartyParticipants(postId.value, 0 ,10).onSuccess { response ->
                _readViewingPartyEvent.value = UiState.Success(ReadViewingPartyEvent.ReadParticipants(response))
                Log.d("fetchViewingPartyParticipants", response.toString())
            }.onFailure {
                Log.d("deleteViewingParty error", it.stackTraceToString())
                _readViewingPartyEvent.value = UiState.Failure("뷰잉파티 참가자를 조회하지 못했습니다")
            }
        }
    }
}