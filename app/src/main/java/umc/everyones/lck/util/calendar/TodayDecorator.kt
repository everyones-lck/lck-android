package umc.everyones.lck.util.calendar

import android.content.Context
import android.graphics.Color
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import umc.everyones.lck.R
import java.time.DayOfWeek

class TodayDecorator (context: Context): DayViewDecorator {
    private val drawable = ContextCompat.getDrawable(context, R.drawable.shape_oval_calendar_circle_line)
    private var date = CalendarDay.today()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable!!)
    }
}