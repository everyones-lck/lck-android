package umc.everyones.lck.domain.repository

import umc.everyones.lck.data.dto.request.TestRequest
import umc.everyones.lck.domain.model.TestModel
import umc.everyones.lck.util.network.NetworkResult

interface TestRepository {
    suspend fun fetchTest(request: TestRequest): NetworkResult<TestModel>
}