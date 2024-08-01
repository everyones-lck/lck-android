package umc.everyones.lck.presentation.lck

data class HistoryData(
    val title: String,
    val details: List<String>,
    var isExpanded: Boolean = false
)