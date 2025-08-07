package umc.everyones.lck.util.calendar

import android.content.Context
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import umc.everyones.lck.R

class SelectedDateDecorator(val context: Context) : DayViewDecorator {
    private var selectedDate: CalendarDay? = null

    fun setSelectedDate(date: CalendarDay?) {
        selectedDate = date
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == selectedDate
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.gray_200)))
    }
}