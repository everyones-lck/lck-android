package umc.everyones.lck.presentation.party

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.NumberPicker
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import umc.everyones.lck.util.calendar.DayDecorator
import umc.everyones.lck.util.calendar.SaturdayDecorator
import umc.everyones.lck.util.calendar.SelectedMonthDecorator
import umc.everyones.lck.util.calendar.SundayDecorator
import umc.everyones.lck.util.calendar.TodayDecorator
import umc.everyones.lck.R
import umc.everyones.lck.databinding.DialogCalendarBinding
import umc.everyones.lck.presentation.base.BaseDialogFragment
import kotlin.math.max
import kotlin.math.min

class CalendarDialogFragment: BaseDialogFragment<DialogCalendarBinding>(R.layout.dialog_calendar) {
    private var onCalendarClickListener: OnCalendarClickListener? = null
    private var selectedDates = listOf<org.threeten.bp.LocalDate>()
    private var anchorView: View? = null

    fun setOnCalendarClickListener(listener: OnCalendarClickListener){
        this.onCalendarClickListener = listener
    }
    override fun initObserver() {

    }

    override fun initView() {
        binding.numberPickerHour.apply {
            maxValue = 12
            minValue = 1
            setFormatter { value ->
                String.format("%02d", value)
            }
        }

        binding.numberPickerMinute.apply {
            maxValue = 59
            minValue = 0
            setFormatter { value ->
                String.format("%02d", value)
            }
        }
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

    fun showBelowView(view: View) {
        anchorView = view
    }

    private fun positionDialogBelowView(view: View, dialog: Dialog) {
        // Calculate the position of the view
        val location = IntArray(2)
        view.getLocationOnScreen(location)

        val dialogWindow = dialog.window ?: return
        dialogWindow.attributes.apply {
            gravity = Gravity.TOP or Gravity.START
            x = location[0] // x coordinate of the view
            y = location[1] + view.height // y coordinate plus the view's height
        }
        dialogWindow.attributes = dialogWindow.attributes
    }
}