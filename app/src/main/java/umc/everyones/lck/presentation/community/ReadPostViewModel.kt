package umc.everyones.lck.presentation.community

import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ReadPostViewModel @Inject constructor(

) : ViewModel() {
    private val _postId = MutableStateFlow<Int>(-1)
    val postId: StateFlow<Int> get() = _postId

    fun setPostId(postId: Int){
        _postId.value = postId
    }
}