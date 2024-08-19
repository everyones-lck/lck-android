package umc.everyones.lck.data.datasourceImpl.party

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import umc.everyones.lck.data.service.party.ViewingPartyService
import umc.everyones.lck.domain.model.response.party.ViewingPartyListModel
import umc.everyones.lck.domain.model.response.party.ViewingPartyParticipantsModel
import javax.inject.Inject

class ViewingPartyParticipantsPagingSource @Inject constructor(
    private val viewingPartyService: ViewingPartyService,
    private val viewingPartyId: Long
) : PagingSource<Int, ViewingPartyParticipantsModel.ParticipantsModel>() {
    override fun getRefreshKey(state: PagingState<Int, ViewingPartyParticipantsModel.ParticipantsModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ViewingPartyParticipantsModel.ParticipantsModel> {
        val page = params.key ?: 0
        if(page != 0) delay(300) else delay(150)
        runCatching {
            viewingPartyService.fetchViewingPartyParticipants(viewingPartyId, page, 10).data.toViewingPartyParticipantsModel()
        }.fold(
            onSuccess = { response ->
                return LoadResult.Page(
                    data = response.participantList,
                    /*response.participantList.toMutableList().apply {
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(100, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(101, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(102, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(103, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(104, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(105, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(106, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(107, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(108, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(109, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(110, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(111, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(112, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(113, "ds", "ds", "ds"))
                                                                          add(ViewingPartyParticipantsModel.ParticipantsModel(114, "ds", "ds", "ds"))
                    },*/
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (response.isLast) null else page + 1
                )
            }, onFailure = {
                Log.d("Paging 3 Load Error", it.stackTraceToString())
                return LoadResult.Error(it)
            }
        )
    }
}