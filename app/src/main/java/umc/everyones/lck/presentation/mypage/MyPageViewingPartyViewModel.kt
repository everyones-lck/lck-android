package umc.everyones.lck.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import umc.everyones.lck.data.models.ViewingPartyItem
import java.util.Calendar
import java.util.Date

class MyPageViewingPartyViewModel : ViewModel() {
    private val _viewingPartyItems = MutableLiveData<List<ViewingPartyItem>>()
    val viewingPartyItems: LiveData<List<ViewingPartyItem>> get() = _viewingPartyItems

    init {
        // 임의의 데이터 생성
        val items = List(10) { index ->
            ViewingPartyItem(
                title = "Title $index",
                date = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, index - 4) }.time // 8월 1일부터 생성
            )
        }
        _viewingPartyItems.value = items
    }

    fun setViewingPartyItems(items: List<ViewingPartyItem>) {
        _viewingPartyItems.value = items
    }
}
