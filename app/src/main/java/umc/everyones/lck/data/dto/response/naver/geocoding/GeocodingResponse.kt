package umc.everyones.lck.data.dto.response.naver.geocoding

data class GeocodingResponse(
    val status: String,
    val meta: Meta,
    val addresses: List<Addresses>,
    val errorMessage: String
)
