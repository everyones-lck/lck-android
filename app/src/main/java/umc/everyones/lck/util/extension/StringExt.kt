package umc.everyones.lck.util.extension

fun String.toCategoryVpPosition(): Int{
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