package umc.everyones.lck.presentation.party

import android.icu.util.LocaleData
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import kotlinx.coroutines.flow.collect
import kr.ac.tukorea.whereareu.util.calendar.DayDecorator
import kr.ac.tukorea.whereareu.util.calendar.SaturdayDecorator
import kr.ac.tukorea.whereareu.util.calendar.SelectedMonthDecorator
import kr.ac.tukorea.whereareu.util.calendar.SundayDecorator
import kr.ac.tukorea.whereareu.util.calendar.TodayDecorator
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogCalendarBinding
import umc.everyones.lck.presentation.base.BaseDialogFragment
import java.time.LocalDate

class CalendarDialogFragment: BaseDialogFragment<DialogCalendarBinding>(R.layout.dialog_calendar) {
    private var onCalendarClickListener: OnCalendarClickListener? = null
    private var selectedDates = listOf<org.threeten.bp.LocalDate>()

    fun setOnCalendarClickListener(listener: OnCalendarClickListener){
        this.onCalendarClickListener = listener
    }
    override fun initObserver() {

    }

    override fun initView() {
        val dayDecorator = DayDecorator(requireContext())
        val todayDecorator = TodayDecorator(requireContext())
        val sundayDecorator = SundayDecorator(requireContext())
        val saturdayDecorator = SaturdayDecorator(requireContext())
        var selectedMonthDecorator = SelectedMonthDecorator(requireContext(), CalendarDay.today().month)

        binding.calendarView.setTitleFormatter { day ->
            val inputText = day.date
            val calendarHeaderElements = inputText.toString().split("-")
            val calendarHeaderBuilder = StringBuilder()

            calendarHeaderBuilder.append(calendarHeaderElements[0]).append("년 ")
                .append(calendarHeaderElements[1]).append("월")

            calendarHeaderBuilder.toString()
        }

        binding.calendarView.addDecorators(dayDecorator, todayDecorator, sundayDecorator, saturdayDecorator, selectedMonthDecorator)
        binding.calendarView.setWeekDayFormatter(ArrayWeekDayFormatter(resources.getTextArray(R.array.custom_weekdays)))

        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            selectedDates = binding.calendarView.selectedDates.map { it.date }
            if (selectedDates.isEmpty()){
                return@setOnDateChangedListener
            }
        }

        binding.calendarView.setOnMonthChangedListener { widget, date ->
            binding.calendarView.removeDecorators()
            binding.calendarView.invalidateDecorators()

            // Decorators 추가
            selectedMonthDecorator = SelectedMonthDecorator(requireContext(), date.month)
            binding.calendarView.addDecorators(dayDecorator, todayDecorator, sundayDecorator, saturdayDecorator, selectedMonthDecorator)
        }

    }

    private fun dismissDialog(){
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    interface OnCalendarClickListener{
        fun onClick(date: CalendarDay)
    }
}