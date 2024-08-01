package umc.everyones.lck.data

import umc.everyones.lck.User

interface UserDataSource {
    suspend fun insertUser(user: User)
    suspend fun getUserByNickname(nickname: String): User?
}
