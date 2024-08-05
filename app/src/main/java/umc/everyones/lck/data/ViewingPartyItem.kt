package umc.everyones.lck.data.models

import java.text.SimpleDateFormat
import java.util.*

data class ViewingPartyItem(
    val title: String,
    val date: Date
) {
    fun getFormattedDate(): String {
        val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return format.format(date)
    }
}
