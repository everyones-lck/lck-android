package umc.everyones.lck.presentation.party

import android.util.Log
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.response.party.ViewingPartyListModel
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository

@HiltViewModel
class ViewingPartyViewModel @Inject constructor(
    private val repository: ViewingPartyRepository
) : ViewModel() {
    // Add ViewModel logic here
    fun fetchViewingPartyList() {
        viewModelScope.launch {
            repository.fetchViewingPartyList(0, 10).onSuccess {
                Log.d("fetchViewingPartyList", it.toString())
            }.onFailure {
                Log.d("fetchViewingPartyList error", it.stackTraceToString())
            }
        }
    }

    val viewingPartyListPage = repository.fetchPagingSource().cachedIn(viewModelScope)

}

