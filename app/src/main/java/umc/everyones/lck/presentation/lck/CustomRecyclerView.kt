
package umc.everyones.lck.presentation.lck

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                // 터치가 시작될 때 부모의 스크롤을 막음
                parent.requestDisallowInterceptTouchEvent(true)
            }
        }
        return super.onInterceptTouchEvent(e)
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // 터치가 끝날 때 부모의 스크롤 허용
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.onTouchEvent(e)
    }
}
