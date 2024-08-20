package umc.everyones.lck.presentation.community.write

import android.util.Log
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import umc.everyones.lck.domain.model.request.community.WriteCommunityRequestModel
import umc.everyones.lck.domain.repository.community.CommunityRepository

@HiltViewModel
class WritePostViewModel @Inject constructor(
    private val repository: CommunityRepository
) : ViewModel() {
    fun writeCommunityPost(
        files: List<MultipartBody.Part>,
        postType: String,
        postTitle: String,
        postContent: String
    ) {
        viewModelScope.launch {
            repository.writeCommunityPost(
                WriteCommunityRequestModel(
                    files,
                    WriteCommunityRequestModel.WriteRequestModel(postType, postTitle, postContent)
                )
            ).onSuccess { response ->
                Log.d("writeCommunity", response.toString())
            }.onFailure {
                Log.d("writeCommunity error", it.stackTraceToString())
            }
        }
    }
}