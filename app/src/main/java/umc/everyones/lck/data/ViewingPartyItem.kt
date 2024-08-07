package umc.everyones.lck.data.models

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ViewingPartyItem(
    val title: String,
    val date: Date
) {
    val formattedDate: String
        get() {
            val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            val formatted = dateFormat.format(date)
            Log.d("FormattedDate", "Formatted Date: $formatted")
            return formatted
        }
    val visibility: Int
        get() = if (date.after(Date())) android.view.View.GONE else android.view.View.VISIBLE
}
