package umc.everyones.lck.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import umc.everyones.lck.data.models.ViewingPartyItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyPageViewingPartyViewModel : ViewModel() {
    private val _viewingPartyItems = MutableLiveData<List<ViewingPartyItem>>()
    val viewingPartyItems: LiveData<List<ViewingPartyItem>> get() = _viewingPartyItems

    init {
        // 테스트 데이터 추가
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val items = List(10) { index ->
            ViewingPartyItem(
                title = "Title $index",
                date = dateFormat.parse("2024-08-${index + 1}") ?: Date()
            )
        }
        _viewingPartyItems.value = items
    }

    fun setViewingPartyItems(items: List<ViewingPartyItem>) {
        _viewingPartyItems.value = items
    }
}
