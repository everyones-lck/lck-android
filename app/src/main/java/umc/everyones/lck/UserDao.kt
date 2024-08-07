package umc.everyones.lck

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface UserDao {

    // 사용자 추가
    @Insert
    suspend fun insertUser(user: User)

    // 사용자 수정
    @Update
    suspend fun updateUser(user: User)

    // 사용자 삭제
    @Delete
    suspend fun deleteUser(user: User)

    // ID로 사용자 조회
    @Query("SELECT * FROM User WHERE userId = :id")
    suspend fun getUserById(id: Int): User?

    // 닉네임으로 사용자 조회
    @Query("SELECT * FROM User WHERE nickname = :nickname")
    suspend fun getUserByNickname(nickname: String): User?

    // 모든 사용자 조회
    @Query("SELECT * FROM User")
    suspend fun getAllUsers(): List<User>

    // 모든 사용자 삭제
    @Query("DELETE FROM User")
    suspend fun deleteAllUsers()
}
