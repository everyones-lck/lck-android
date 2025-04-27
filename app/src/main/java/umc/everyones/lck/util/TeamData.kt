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

/*    val myteamLogos = mapOf(
        R.id.iv_mypage_myteam_gen_g to 2,
        R.id.iv_mypage_myteam_hanhwa to 3,
        R.id.iv_mypage_myteam_dplus_kia to 4,
        R.id.iv_mypage_myteam_t1 to 5,
        R.id.iv_mypage_myteam_kt_rolster to 6,
        R.id.iv_mypage_myteam_kwangdong_freecs to 7,
        R.id.iv_mypage_myteam_bnk to 8,
        R.id.iv_mypage_myteam_nongshim_red_force to 9,
        R.id.iv_mypage_myteam_drx to 10,
        R.id.iv_mypage_myteam_ok_saving_bank_brion to 11
    )*/

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
        1 to R.drawable.img_mypage_empty_background,
        2 to R.drawable.img_mypage_gen_g_background,
        3 to R.drawable.img_mypage_hanhwa_background,
        4 to R.drawable.img_mypage_dplus_kia_background,
        5 to R.drawable.img_mypage_t1_background,
        6 to R.drawable.img_mypage_kt_rolster_background,
        7 to R.drawable.img_mypage_kwangdong_background,
        8 to R.drawable.img_mypage_bnk_background,
        9 to R.drawable.img_mypage_nongshim_red_force_background,
        10 to R.drawable.img_mypage_drx_background,
        11 to R.drawable.img_mypage_ok_saving_bank_biron_background
    )

    val mypageMyteam = mapOf(
        1 to R.drawable.ic_mypage_myteam_empty,
        2 to R.drawable.ic_mypage_myteam_gen_g,
        3 to R.drawable.ic_mypage_myteam_hanhwa,
        4 to R.drawable.ic_mypage_myteam_dplus_kia,
        5 to R.drawable.ic_mypage_myteam_t1,
        6 to R.drawable.ic_mypage_myteam_kt_rolster,
        7 to R.drawable.ic_mypage_myteam_kwangdong_freecs,
        8 to R.drawable.ic_mypage_myteam_bnk,
        9 to R.drawable.ic_mypage_myteam_nongshim_red_force,
        10 to R.drawable.ic_mypage_myteam_drx,
        11 to R.drawable.ic_mypage_myteam_ok_saving_bank_brion
    )

    fun getSignupSuccessTeamLogo(teamId: Int): Int {
        return signupSuccessTeamBackground[teamId] ?: android.R.color.transparent
    }

    fun getMypageTeamBackground(teamId: Int): Int {
        return mypageTeamBackground[teamId] ?: android.R.color.transparent
    }
}