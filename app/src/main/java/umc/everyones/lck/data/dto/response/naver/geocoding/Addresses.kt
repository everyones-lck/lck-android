package umc.everyones.lck.data.dto.response.naver.geocoding

data class Addresses(
    val roadAddress: String,
    val jibunAddress: String,
    val englishAddress: String,
    val addressElements: List<AddressElements>,
    val x: String,
    val y: String,
    val distance: Double
)
