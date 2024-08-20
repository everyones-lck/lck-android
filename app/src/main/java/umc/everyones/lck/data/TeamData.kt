package umc.everyones.lck.util

import umc.everyones.lck.R

object TeamData {

    val teamLogos = mapOf(
        R.id.iv_signup_myteam_gen_g to 2,
        R.id.iv_signup_myteam_hanhwa to 3,
        R.id.iv_signup_myteam_dplus_kia to 4,
        R.id.iv_signup_myteam_t1 to 5,
        R.id.iv_signup_myteam_kt_rolster to 6,
        R.id.iv_signup_myteam_kwangdong_freecs to 7,
        R.id.iv_signup_myteam_bnk to 8,
        R.id.iv_signup_myteam_nongshim_red_force to 9,
        R.id.iv_signup_myteam_drx to 10,
        R.id.iv_signup_myteam_ok_saving_bank_brion to 11,
    )

    val myteamLogos = mapOf(
        R.id.iv_mypage_myteam_hanhwa to "Hanhwa",
        R.id.iv_mypage_myteam_gen_g to "Gen.G",
        R.id.iv_mypage_myteam_t1 to "T1",
        R.id.iv_mypage_myteam_kwangdong_freecs to "Kwangdong Freecs",
        R.id.iv_mypage_myteam_bnk to "BNK",
        R.id.iv_mypage_myteam_nongshim_red_force to "Nongshim Red Force",
        R.id.iv_mypage_myteam_drx to "DRX",
        R.id.iv_mypage_myteam_ok_saving_bank_brion to "OK Saving Bank Brion",
        R.id.iv_mypage_myteam_dplus_kia to "Dplus Kia",
        R.id.iv_mypage_myteam_kt_rolster to "KT Rolster"
    )

    val signupSuccessTeamBackground = mapOf(
        2 to R.drawable.img_signup_success_gen_g_logo,
        3 to R.drawable.img_signup_success_hanhwa_logo,
        4 to R.drawable.img_signup_success_dplus_kia_logo,
        5 to R.drawable.img_signup_success_t1_logo,
        6 to R.drawable.img_signup_success_kt_rolster_logo,
        7 to R.drawable.img_signup_success_kwangdong_freecs_logo,
        8 to R.drawable.img_signup_success_bnk_logo,
        9 to R.drawable.img_signup_success_nongshim_red_force_logo,
        10 to R.drawable.img_signup_success_drx_logo,
        11 to R.drawable.img_signup_success_ok_saving_bank_brion_logo
    )

    val mypageTeamBackground = mapOf(
        1 to R.drawable.img_mypage_empty_background,
        "Hanhwa" to R.drawable.img_mypage_hanhwa_background,
        "Gen.G" to R.drawable.img_mypage_gen_g_background,
        "T1" to R.drawable.img_mypage_t1_background,
        "Kwangdong Freecs" to R.drawable.img_mypage_kwangdong_background,
        "BNK" to R.drawable.img_mypage_bnk_background,
        "Nongshim Red Force" to R.drawable.img_mypage_nongshim_red_force_background,
        "DRX" to R.drawable.img_mypage_drx_background,
        "OK Saving Bank Brion" to R.drawable.img_mypage_ok_saving_bank_biron_background,
        "Dplus Kia" to R.drawable.img_mypage_dplus_kia_background,
        "KT Rolster" to R.drawable.img_mypage_kt_rolster_background
    )

    fun getSignupSuccessTeamLogo(teamId: Int): Int {
        return signupSuccessTeamBackground[teamId] ?: android.R.color.transparent
    }

    fun getMypageTeamBackground(teamId: Int): Int {
        return mypageTeamBackground[teamId] ?: android.R.color.transparent
    }
}
