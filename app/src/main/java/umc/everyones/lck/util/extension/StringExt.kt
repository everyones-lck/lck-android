package umc.everyones.lck.util.extension

import android.annotation.SuppressLint
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
fun LocalDateTime.listPartyDateToString(): String {
    val simpleDateFormat = SimpleDateFormat("yy.MM.dd")
    return simpleDateFormat.format(this)
}

@SuppressLint("SimpleDateFormat")
fun String.partyDateToString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    LocalDateTime.parse(this, formatter)

    val simpleDateFormat = SimpleDateFormat("yy.MM.dd hh:mm")
    return simpleDateFormat.format(LocalDateTime.parse(this, formatter))
}

fun String.toViewingPartyReadDate(): String {
    val temp = this.split("|")
    return "${temp[0].replace("/","").replace("  ", ".").trim()} ${temp[1].trim()}"
}