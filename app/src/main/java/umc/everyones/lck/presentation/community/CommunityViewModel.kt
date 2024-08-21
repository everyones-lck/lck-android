package umc.everyones.lck.presentation.community

import android.util.Log
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.repository.community.CommunityRepository
import umc.everyones.lck.util.network.EventFlow
import umc.everyones.lck.util.network.MutableEventFlow

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepository
) : ViewModel() {

    val smallTalkListPage = repository.fetchPagingSource("잡담").cachedIn(viewModelScope)
    val supportListPage = repository.fetchPagingSource("응원").cachedIn(viewModelScope)
    val freeAgentListPage = repository.fetchPagingSource("FA").cachedIn(viewModelScope)
    val tradeListPage = repository.fetchPagingSource("거래").cachedIn(viewModelScope)
    val questionListPage = repository.fetchPagingSource("질문").cachedIn(viewModelScope)
    val reviewListPage = repository.fetchPagingSource("후기").cachedIn(viewModelScope)

    private val _categoryNeedsRefresh = MutableEventFlow<String>(replay = 1)
    val categoryNeedsRefresh: EventFlow<String> get() = _categoryNeedsRefresh
    fun fetchCommunityList(postType: String, page: Int, size: Int){
        viewModelScope.launch {
            repository.fetchCommunityList(postType, page, size).onSuccess {  response ->
                Log.d("fetchCommunityList", response.toString())
            }.onFailure {
                Log.d("fetchCommunityList error", it.stackTraceToString())
            }
        }
    }

    fun refreshCategoryPage(category: String){
        viewModelScope.launch {
            _categoryNeedsRefresh.emit(category)
        }
    }

}