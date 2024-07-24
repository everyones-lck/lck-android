package umc.everyones.lck.util.extension

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

import com.google.android.material.snackbar.Snackbar
import umc.everyones.lck.R


fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Fragment.snackBar(anchorView: View, message: () -> String) {
    Snackbar.make(anchorView, message(), Snackbar.LENGTH_SHORT).show()
}

fun Fragment.stringOf(@StringRes resId: Int) = getString(resId)

fun Fragment.colorOf(@ColorRes resId: Int) = ContextCompat.getColor(requireContext(), resId)

fun Fragment.drawableOf(@DrawableRes resId: Int) =
    ContextCompat.getDrawable(requireContext(), resId)

fun Fragment.setStatusBarColor(colorResId: Int) {
    activity?.let {
        val statusBarColor = ContextCompat.getColor(it, colorResId)
        it.window.statusBarColor = statusBarColor
    }
}

val Fragment.viewLifeCycle
    get() = viewLifecycleOwner.lifecycle

val Fragment.viewLifeCycleScope
    get() = viewLifecycleOwner.lifecycleScope

// 커스텀 스낵바 확장 함수
fun Fragment.showCustomSnackBar(view: View, message: String){
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)

    val snackBarView = snackBar.view
    val parentView = snackBarView.parent

    if (parentView is CoordinatorLayout) {
        val params = snackBarView.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(50, 50, 50, 100)  // 원하는 마진 값 설정
        snackBarView.layoutParams = params
    } else if (parentView is FrameLayout) {
        val params = snackBarView.layoutParams as FrameLayout.LayoutParams
        params.setMargins(50, 50, 50, 100)  // 원하는 마진 값 설정
        snackBarView.layoutParams = params
    }

    val textView: TextView = snackBar.view.findViewById(com.google.android.material.R.id.snackbar_text)
    textView.setTextAppearance(R.style.TextAppearance_LCK_Warning)
    snackBar.setBackgroundTint(this.colorOf(R.color.white))

    snackBar.show()
}