package umc.everyones.lck.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import umc.everyones.lck.data.models.ViewingPartyItem

class MyPageViewingPartyViewModel : ViewModel() {
    private val _viewingPartyItems = MutableLiveData<List<ViewingPartyItem>>()
    val viewingPartyItems: LiveData<List<ViewingPartyItem>> get() = _viewingPartyItems

    fun setViewingPartyItems(items: List<ViewingPartyItem>) {
        _viewingPartyItems.value = items
    }
}
