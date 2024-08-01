package umc.everyones.lck

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @ColumnInfo(name = "nickname") val name: String,
    @ColumnInfo(name = "profileUri") val profileUri: String = "",
    @ColumnInfo(name = "team") val team: String = "default_team",
    @ColumnInfo(name = "tier") val tier: String = "Bronze",
    // 고유키 autoGenerate 자동으로 증가
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "userId") val userId: Int = 0
)
