package umc.everyones.lck.data.dto.response.naver.geocoding

import com.naver.maps.geometry.LatLng
import umc.everyones.lck.domain.model.naver.GeocodingModel

data class GeocodingResponseDto(
    val status: String,
    val meta: Meta,
    val addresses: List<Addresses>,
    val errorMessage: String
) {
    fun toGeocodingModel(): GeocodingModel {
        with(addresses[0]) {
            return GeocodingModel(
                LatLng(y.toDouble(), x.toDouble()),
                extractAddress(addressElements).trim(),
                roadAddress,
                jibunAddress
            )
        }
    }

    private fun extractAddress(elements: List<AddressElements>): String {
        val desiredTypes = listOf("SIDO", "SIGUGUN", "DONGMYUN")

        return elements.filter { element ->
            element.types.any { it in desiredTypes }
        }.joinToString(" ") { element ->
            if (element.types.contains("SIDO")) {
                if (element.longName.length > 3) {
                    "${element.longName.slice(0..1)}시"
                }
                else {
                    ""
                }
            } else {
                element.longName
            }
        }
    }
}
