package umc.everyones.lck.data.dto.response.naver.reversegeocoding

import umc.everyones.lck.domain.model.naver.ReverseGeocodingModel

data class ReverseGeocodingResponseDto(
    val status: StatusResult,
    val results: List<ReverseGeoCodingResult>
) {

    fun toReverseGeocodingModel(): ReverseGeocodingModel {
        val result = results.firstOrNull()
        val adminAddress = if(result != null){
            with(result.region){
                if (area1.name.length > 3){
                    "${area1.alias}시 ${area2.name} ${area3.name}"
                } else {
                     "${area2.name.split("")[0]} ${area3.name}"
                }
            }
        } else {
            ""
        }
        return ReverseGeocodingModel(adminAddress)
    }

    data class StatusResult(
        val code: Int,
        val name: String,
        val message: String
    )

    data class ReverseGeoCodingResult(
        val region: RegionInfo,
        val land: Land
    ) {
        data class RegionInfo(
            val area1: AreaInfo,
            val area2: AreaInfo,
            val area3: AreaInfo,
            val area4: AreaInfo,
        ) {
            data class AreaInfo(
                val name: String,
                val coords: Coords,
                val alias: String
            ){
                data class Coords(
                    val center: Center
                )

                data class Center(
                    val crs: String,
                    val x: Float,
                    val y: Float
                )
            }
        }
    }
}
