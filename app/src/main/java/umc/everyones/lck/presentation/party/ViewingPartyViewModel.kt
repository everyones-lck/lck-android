package umc.everyones.lck.presentation.party

import android.util.Log
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.response.party.ViewingPartyListModel
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository
import umc.everyones.lck.util.network.EventFlow
import umc.everyones.lck.util.network.MutableEventFlow

@HiltViewModel
class ViewingPartyViewModel @Inject constructor(
    private val repository: ViewingPartyRepository
) : ViewModel() {
    private var _viewingPartyListPage = repository.fetchViewingPartyListPagingSource().cachedIn(viewModelScope)

    val viewingPartyListPage: Flow<PagingData<ViewingPartyListModel.ViewingPartyElementModel>>
        get() = _viewingPartyListPage
    
    private val _isRefreshNeeded = MutableEventFlow<Boolean>()
    val isRefreshNeeded: EventFlow<Boolean> get() = _isRefreshNeeded
    fun setIsRefreshNeeded(isRefreshNeeded: Boolean){
        viewModelScope.launch {
            _isRefreshNeeded.emit(isRefreshNeeded)
        }
    }
}

