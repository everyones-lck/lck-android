package umc.everyones.lck.presentation.party.write

import android.util.Log
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import umc.everyones.lck.domain.repository.NaverRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import umc.everyones.lck.domain.model.naver.GeocodingModel
import umc.everyones.lck.domain.model.request.party.WriteViewingPartyModel
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository
import java.lang.IndexOutOfBoundsException

@HiltViewModel
class WriteViewingPartyViewModel @Inject constructor(
    private val naverRepository: NaverRepository,
    private val repository: ViewingPartyRepository
) : ViewModel() {
    private val _geocodingResult = MutableSharedFlow<GeocodingModel>()
    val geocodingResult: SharedFlow<GeocodingModel> get() = _geocodingResult

    private val _date = MutableSharedFlow<String>()
    val date: SharedFlow<String> get() = _date

    private val _writeViewingPartyEvent = MutableSharedFlow<WriteViewingPartyEvent>()
    val writeViewingPartyEvent: SharedFlow<WriteViewingPartyEvent> get() = _writeViewingPartyEvent

    sealed class WriteViewingPartyEvent{
        data object WriteDoneViewingParty: WriteViewingPartyEvent()
    }

    private fun eventWriteViewingParty(event: WriteViewingPartyEvent){
        viewModelScope.launch {
            _writeViewingPartyEvent.emit(event)
        }
    }

    fun setDate(date: String) {
        viewModelScope.launch {
            _date.emit(date)
        }
    }

    fun fetchGeocoding(query: String) {
        viewModelScope.launch {
            naverRepository.fetchGeocoding(query).onSuccess { response ->
                Log.d("fetchGeocoding", response.toString())
                _geocodingResult.emit(response)
            }.onFailure {
                if (it is IndexOutOfBoundsException) {
                    _geocodingResult.emit(GeocodingModel(LatLng.INVALID, "", "", ""))
                }
                Log.d("fetchGeocoding", it.stackTraceToString())
            }
        }
    }

    /*fun fetchReverseGeocoding(coord: LatLng){
        viewModelScope.launch {
            naverRepository.fetchReverseGeocodingInfo(
                "${coord.longitude},${coord.latitude}"
            ).onSuccess {response ->
                Log.d("fetchReverseGeocoding", response.toString())
                val result = response.results.firstOrNull()
                if(result != null){
                    with(result.region){
                        _adminAddress.value = "${area2.name.split(" ")[0]} ${area3.name}"

                    }
                }
            }
        }
    }*/

    fun writeViewingParty(
        isEdit: Boolean,
        postId: Long = 0L,
        name: String,
        date: String,
        latitude: Double,
        longitude: Double,
        location: String,
        price: String,
        lowParticipate: String,
        highParticipate: String,
        qualify: String,
        etc: String
    ) {
        val writeViewingPartyModel = WriteViewingPartyModel(
            name,
            date,
            latitude,
            longitude,
            location,
            price,
            lowParticipate,
            highParticipate,
            qualify,
            etc
        )
        Log.d("isEdit", isEdit.toString())
        if (isEdit) {
            viewModelScope.launch {
                repository.editViewingParty(
                    postId,
                    writeViewingPartyModel
                ).onSuccess { response ->
                    Log.d("editViewingParty", response.toString())
                }.onFailure {
                    Log.d("editViewingParty", it.stackTraceToString())
                }
            }
        } else {
            viewModelScope.launch {
                repository.writeViewingParty(
                    writeViewingPartyModel
                ).onSuccess { response ->
                    Log.d("writeViewingParty", response.toString())
                    eventWriteViewingParty(WriteViewingPartyEvent.WriteDoneViewingParty)
                }.onFailure {
                    Log.d("writeViewingParty", it.stackTraceToString())
                }
            }
        }
    }
}