package umc.everyones.lck.data.dto.response

import umc.everyones.lck.domain.model.TestModel

data class TestResponse (
    val body: String
){
    fun toTestModel() = TestModel(body)
}