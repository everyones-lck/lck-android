package umc.everyones.lck.util.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDateTime

// 카테고리 -> 포지션 확장함수
fun String.toCategoryPosition(): Int{
    return when (this) {
        "잡담" -> 0
        "응원" -> 1
        "FA" -> 2
        "거래" -> 3
        "질문" -> 4
        "후기" -> 5
        else -> 0
    }
}

fun String.combineNicknameAndTeam(team: String): String{
    return "$this | $team"
}

@SuppressLint("SimpleDateFormat")
fun LocalDateTime.toPartyDateToString(): String {
    val simpleDateFormat = SimpleDateFormat("yy.MM.dd")
    return simpleDateFormat.format(this)
}