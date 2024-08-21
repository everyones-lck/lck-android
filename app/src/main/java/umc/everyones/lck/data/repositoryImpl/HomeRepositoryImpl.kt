package umc.everyones.lck.data.repositoryImpl

import umc.everyones.lck.data.datasource.HomeDataSource
import umc.everyones.lck.domain.model.response.home.HomeTodayMatchModel
import umc.everyones.lck.domain.repository.home.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource
): HomeRepository {
    override suspend fun fetchHomeTodayMatchInformation(): Result<HomeTodayMatchModel> = runCatching {
        homeDataSource.fetchHomeTodayMatchInformation().data.toHomeTodayMatchModel()
    }
}