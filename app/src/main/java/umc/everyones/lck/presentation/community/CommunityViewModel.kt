package umc.everyones.lck.presentation.community

import android.util.Log
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.repository.community.CommunityRepository

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepository
) : ViewModel() {

    fun fetchCommunityList(postType: String, page: Int, size: Int){
        viewModelScope.launch {
            repository.fetchCommunityList(postType, page, size).onSuccess {  response ->
                Log.d("fetchCommunityList", response.toString())
            }.onFailure {
                Log.d("fetchCommunityList error", it.stackTraceToString())
            }
        }
    }
}