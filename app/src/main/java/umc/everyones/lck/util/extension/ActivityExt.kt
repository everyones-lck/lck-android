package umc.everyones.lck.util.extension

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import umc.everyones.lck.R

// 커스텀 스낵바 확장 함수
fun Activity.showCustomSnackBar(view: View, message: String){
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

fun Activity.hideKeyboardOnOutsideTouch(event: MotionEvent?, editText: EditText, inside: ConstraintLayout) {
    if (event?.action == MotionEvent.ACTION_DOWN) {
        val outRect = Rect()
        inside.getGlobalVisibleRect(outRect)
        if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
            editText.clearFocus()
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        }
    }
}