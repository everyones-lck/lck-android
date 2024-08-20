package umc.everyones.lck.presentation.community.write

import android.util.Log
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import umc.everyones.lck.domain.model.request.community.EditCommunityRequestModel
import umc.everyones.lck.domain.model.request.community.WriteCommunityRequestModel
import umc.everyones.lck.domain.repository.community.CommunityRepository
import umc.everyones.lck.util.network.UiState

@HiltViewModel
class WritePostViewModel @Inject constructor(
    private val repository: CommunityRepository
) : ViewModel() {
    private val _postId = MutableStateFlow<Long>(-1)
    val postId: StateFlow<Long> get() = _postId

    fun setPostId(postId: Long){
        _postId.value = postId
    }

    private val _writeDoneEvent = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val writeDoneEvent: StateFlow<UiState<Boolean>> get() = _writeDoneEvent

    fun writeCommunityPost(
        files: List<MultipartBody.Part?>,
        postType: String,
        postTitle: String,
        postContent: String
    ) {
        viewModelScope.launch {
            _writeDoneEvent.value = UiState.Loading
            repository.writeCommunityPost(
                WriteCommunityRequestModel(
                    files,
                    WriteCommunityRequestModel.WriteRequestModel(postType, postTitle, postContent)
                )
            ).onSuccess { response ->
                Log.d("writeCommunity", response.toString())
                _writeDoneEvent.value = UiState.Success(true)
            }.onFailure {
                Log.d("writeCommunity error", it.stackTraceToString())
                _writeDoneEvent.value = UiState.Failure("커뮤니티 게시글 작성에 실패했습니다")
            }
        }
    }

    fun editCommunityPost(postType: String, postTitle: String, postContent: String){
        viewModelScope.launch {
            _writeDoneEvent.value = UiState.Loading
            repository.editCommunityPost(postId.value, EditCommunityRequestModel(
                postType, postTitle, postContent
            )
            ).onSuccess { response ->
                Log.d("editCommunityPost", response.toString())
                _writeDoneEvent.value = UiState.Success(true)
            }.onFailure {
                Log.d("editCommunityPost error", it.stackTraceToString())
                _writeDoneEvent.value = UiState.Failure("커뮤니티 게시글 수정에 실패했습니다")
            }
        }
    }
}