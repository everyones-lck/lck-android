package umc.everyones.lck.util.calendar

import android.content.Context
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import org.threeten.bp.DayOfWeek
import umc.everyones.lck.R

class SelectedSaturdayDecorator (val context: Context, val selectedDay: Int) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return selectedDay == day.day
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(object: ForegroundColorSpan(ContextCompat.getColor(context, R.color.white)){})
    }
}