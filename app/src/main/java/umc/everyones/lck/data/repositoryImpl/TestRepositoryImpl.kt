package umc.everyones.lck.data.repositoryImpl

import umc.everyones.lck.data.dto.BaseResponse
import umc.everyones.lck.data.dto.request.TestRequest
import umc.everyones.lck.data.dto.response.TestResponse
import umc.everyones.lck.data.service.TestService
import umc.everyones.lck.domain.model.TestModel
import umc.everyones.lck.domain.repository.TestRepository
import umc.everyones.lck.util.network.NetworkResult
import umc.everyones.lck.util.network.handleApi
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val testService: TestService
) : TestRepository {
    override suspend fun fetchTest(request: TestRequest): NetworkResult<TestModel> {
        return handleApi({testService.fetchTest(request)}) {response: BaseResponse<TestResponse> -> response.data.toTestModel()} // mapper를 통해 api 결과를 TestModel로 매핑
    }

}