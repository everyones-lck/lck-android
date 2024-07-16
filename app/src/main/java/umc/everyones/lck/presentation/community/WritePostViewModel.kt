package umc.everyones.lck.presentation.community

import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class WritePostViewModel @Inject constructor(

) : ViewModel() {
    private val _selectedCategory = MutableSharedFlow<Int>(replay = 1)
    val selectedCategory: SharedFlow<Int> get() = _selectedCategory



    fun setSelectedCategory(category: String) {
        val position = when(category) {
            "잡담" -> 0
            "응원" -> 1
            "FA" -> 2
            "거래" -> 3
            "질문" -> 4
            "후기" -> 5
            else -> 0
        }
        viewModelScope.launch {
            _selectedCategory.emit(position)
        }
    }
}