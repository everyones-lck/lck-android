package umc.everyones.lck.data.dto.response.naver.geocoding

data class AddressElements(
    val types: List<String>,
    val longName: String,
    val shortName: String,
    val code: String
)
