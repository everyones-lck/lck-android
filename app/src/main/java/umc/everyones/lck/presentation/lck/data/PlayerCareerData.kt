package umc.everyones.lck.presentation.lck.data

data class PlayerCareerData(
    val title: String,
    val details: List<String>,
    var isExpanded: Boolean = false
)
