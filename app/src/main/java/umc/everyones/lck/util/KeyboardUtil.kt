package umc.everyones.lck.util

import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowMetrics
import androidx.core.widget.NestedScrollView
import androidx.window.layout.WindowMetricsCalculator

object KeyboardUtil {
    private var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    fun registerKeyboardVisibilityListener(rootView: View, scrollView: NestedScrollView) {
        globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            Log.d("rect bottom", rect.bottom.toString())
            Log.d("screenHeight", screenHeight.toString())
            Log.d("scrollView", scrollView.bottom.toString())
            Log.d("keypadHeight", keypadHeight.toString())

            // 키보드가 화면의 20% 이상 차지할 경우
            if (keypadHeight > screenHeight * 0.2) {
                scrollView.post {
                    scrollView.scrollTo(0, screenHeight)
                }
            } else {
                // 키보드가 사라졌을 때의 처리
            }
        }
        rootView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    fun unregisterKeyboardVisibilityListener(rootView: View) {
        globalLayoutListener?.let {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(it)
        }
    }
}