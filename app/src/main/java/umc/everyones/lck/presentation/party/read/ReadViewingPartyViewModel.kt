package umc.everyones.lck.presentation.party.read

import android.util.Log
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository
import umc.everyones.lck.util.network.onSuccess

@HiltViewModel
class ReadViewingPartyViewModel @Inject constructor(
    private val repository: ViewingPartyRepository
) : ViewModel() {
    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> get() = _title

    fun setTitle(title: String){
        _title.value = title
    }

    fun fetchViewingParty(postId: Long){
        viewModelScope.launch {
            repository.fetchViewingParty(postId).onSuccess {
                Log.d("fetchViewingParty", it.toString())
            }
        }
    }
}