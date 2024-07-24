package umc.everyones.lck.presentation.party

import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ReadViewingPartyViewModel @Inject constructor(

) : ViewModel() {
    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> get() = _title

    fun setTitle(title: String){
        _title.value = title
    }
}