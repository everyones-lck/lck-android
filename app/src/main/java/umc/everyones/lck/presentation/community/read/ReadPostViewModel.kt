package umc.everyones.lck.presentation.community.read

import android.content.SharedPreferences
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
import umc.everyones.lck.domain.model.request.community.EditCommunityRequestModel
import umc.everyones.lck.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.lck.domain.repository.community.CommunityRepository
import umc.everyones.lck.util.network.EventFlow
import umc.everyones.lck.util.network.MutableEventFlow
import umc.everyones.lck.util.network.UiState

@HiltViewModel
class ReadPostViewModel @Inject constructor(
    private val repository: CommunityRepository,
    private val spf: SharedPreferences
) : ViewModel() {
    private val _postId = MutableStateFlow<Long>(-1)
    val postId: StateFlow<Long> get() = _postId

    fun setPostId(postId: Long){
        _postId.value = postId
    }

    private val _readCommunityEvent = MutableStateFlow<UiState<ReadCommunityEvent>>(UiState.Empty)
    val readCommunityEvent: StateFlow<UiState<ReadCommunityEvent>> get() = _readCommunityEvent

    private val _isWriter = MutableEventFlow<Boolean>()
    val isWriter: EventFlow<Boolean> get() = _isWriter

    sealed class ReadCommunityEvent{
        data class ReadPost(val post: ReadCommunityResponseModel): ReadCommunityEvent()

        data object EditPost: ReadCommunityEvent()

        data object DeletePost : ReadCommunityEvent()

        data object ReportPost : ReadCommunityEvent()

        data object ReportComment : ReadCommunityEvent()
    }

    fun fetchCommunityPost(){
        viewModelScope.launch {
            _readCommunityEvent.value = UiState.Loading
            repository.fetchCommunityPost(postId.value).onSuccess { response ->
                Log.d("fetchCommunityPost", response.toString())
                _readCommunityEvent.value = UiState.Success(ReadCommunityEvent.ReadPost(response))
                _isWriter.emit(spf.getString("nickname", "") == response.writerInfo.split("|")[0].trim())
            }.onFailure {
                Log.d("fetchCommunityPost error", it.stackTraceToString())
                _readCommunityEvent.value = UiState.Failure("커뮤니티 게시글 상세조회에 실패했습니다")
            }
        }
    }

    fun deleteCommunityPost(){
        viewModelScope.launch{
            repository.deleteCommunityPost(postId.value).onSuccess { response ->
                Log.d("deleteCommunityPost", response.toString())
                _readCommunityEvent.value = UiState.Success(ReadCommunityEvent.DeletePost)
            }.onFailure {
                Log.d("deleteCommunityPost error", it.stackTraceToString())
                _readCommunityEvent.value = UiState.Failure("커뮤니티 게시글 삭제에 실패했습니다")
            }
        }
    }
}