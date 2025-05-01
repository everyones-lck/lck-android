package umc.everyones.lck.util

import umc.everyones.lck.R

object TeamData {

    val teamLogos = mapOf(
        R.id.layout_signup_myteam_gen_g to 2,
        R.id.layout_signup_myteam_hanwha to 3,
        R.id.layout_signup_myteam_dk to 4,
        R.id.layout_signup_myteam_t1 to 5,
        R.id.layout_signup_myteam_kt to 6,
        R.id.layout_signup_myteam_dn to 7,
        R.id.layout_signup_myteam_bnk to 8,
        R.id.layout_signup_myteam_red to 9,
        R.id.layout_signup_myteam_drx to 10,
        R.id.layout_signup_myteam_brion to 11,
    )

    val teamNames = mapOf(
        1 to "선택된 My Team이 없습니다",
        2 to "GEN",
        3 to "HLE",
        4 to "DK",
        5 to "T1",
        6 to "KT",
        7 to "DN",
        8 to "BNK",
        9 to "RED",
        10 to "DRX",
        11 to "Brion"
    )

    val teamMyPageLogos = mapOf(
        R.id.layout_mypage_myteam_gen_g to 2,
        R.id.layout_mypage_myteam_hanwha to 3,
        R.id.layout_mypage_myteam_dk to 4,
        R.id.layout_mypage_myteam_t1 to 5,
        R.id.layout_mypage_myteam_kt to 6,
        R.id.layout_mypage_myteam_dn to 7,
        R.id.layout_mypage_myteam_bnk to 8,
        R.id.layout_mypage_myteam_red to 9,
        R.id.layout_mypage_myteam_drx to 10,
        R.id.layout_mypage_myteam_brion to 11,
    )

    val signupSuccessTeamBackground = mapOf(
        2 to R.drawable.img_yellow_2,
        3 to R.drawable.img_orange,
        4 to R.drawable.img_red,
        5 to R.drawable.img_red,
        6 to R.drawable.img_red,
        7 to R.drawable.img_blue_2,
        8 to R.drawable.img_yellow,
        9 to R.drawable.img_red,
        10 to R.drawable.img_blue,
        11 to R.drawable.img_green
    )

    val mypageTeamBackground = mapOf(
        2 to R.color.gen_g,
        3 to R.color.hanhwa,
        4 to R.color.dplus_kia,
        5 to R.color.t1,
        6 to R.color.kt_rolster,
        7 to R.color.dn,
        8 to R.color.bnk,
        9 to R.array.red,
        10 to R.color.drx,
        11 to R.color.brion
    )

    fun getSignupSuccessTeamLogo(teamId: Int): Int {
        return signupSuccessTeamBackground[teamId] ?: android.R.color.transparent
    }

    fun getMypageTeamBackground(teamId: Int): Int {
        return mypageTeamBackground[teamId] ?: android.R.color.transparent
    }
}