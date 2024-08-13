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
import umc.everyones.lck.domain.model.request.party.WriteViewingPartyModel
import umc.everyones.lck.domain.repository.party.ViewingPartyRepository
import umc.everyones.lck.util.network.onFail
import umc.everyones.lck.util.network.onSuccess

@HiltViewModel
class WriteViewingPartyViewModel @Inject constructor(
    private val naverRepository: NaverRepository,
    private val repository: ViewingPartyRepository
) : ViewModel() {
    private val _latLng = MutableSharedFlow<LatLng>()
    val latLng: SharedFlow<LatLng> get() = _latLng

    private val _date = MutableSharedFlow<String>()
    val date: SharedFlow<String> get() = _date

    fun setDate(date: String) {
        viewModelScope.launch {
            _date.emit(date)
        }
    }

    fun fetchGeoCoding(query: String) {
        viewModelScope.launch {
            naverRepository.fetchGeocoding(query).onSuccess { response ->
                val result = response.addresses.firstOrNull()
                if (result != null) {
                    Log.d("result", result.toString())
                    val latLng = LatLng(result.y.toDouble(), result.x.toDouble())
                    _latLng.emit(latLng)
                } else {
                    _latLng.emit(LatLng.INVALID)
                }
            }.onFail {
                _latLng.emit(LatLng.INVALID)
            }
        }
    }

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
                    Log.d("writeViewingParty", response.toString())
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
                }.onFailure {

                }
            }
        }
    }
}