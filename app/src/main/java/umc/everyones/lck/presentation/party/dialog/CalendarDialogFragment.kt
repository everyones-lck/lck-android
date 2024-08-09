package umc.everyones.lck.presentation.party.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.Gravity
import android.view.View
import androidx.fragment.app.activityViewModels
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
import umc.everyones.lck.presentation.party.write.WriteViewingPartyViewModel
import java.sql.Date
import java.text.SimpleDateFormat

class CalendarDialogFragment : BaseDialogFragment<DialogCalendarBinding>(R.layout.dialog_calendar) {
    private var selectedDate: String = ""
    private var hour = "00"
    private var minute = "00"
    private val dateFormat = "yyyy / MM / dd"
    private val viewModel: WriteViewingPartyViewModel by activityViewModels()

    override fun initObserver() {

    }

    @SuppressLint("SimpleDateFormat")
    override fun initView() {
        initNumberPicker()

        val simpleDateFormat = SimpleDateFormat(dateFormat)

        val dayDecorator = DayDecorator(requireContext())
        val todayDecorator = TodayDecorator(requireContext())
        val sundayDecorator = SundayDecorator(requireContext())
        val saturdayDecorator = SaturdayDecorator(requireContext())
        var selectedMonthDecorator =
            SelectedMonthDecorator(requireContext(), CalendarDay.today().month)

        binding.calendarView.setTitleFormatter { day ->
            val inputText = day.date
            val calendarHeaderElements = inputText.toString().split("-")
            val calendarHeaderBuilder = StringBuilder()

            calendarHeaderBuilder.append(calendarHeaderElements[0]).append("년 ")
                .append(calendarHeaderElements[1]).append("월")

            calendarHeaderBuilder.toString()
        }

        binding.calendarView.addDecorators(
            dayDecorator,
            todayDecorator,
            sundayDecorator,
            saturdayDecorator,
            selectedMonthDecorator
        )
        binding.calendarView.setWeekDayFormatter(ArrayWeekDayFormatter(resources.getTextArray(R.array.custom_weekdays)))

        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            selectedDate = simpleDateFormat.format(Date.valueOf(date.date.toString()))
            //Date.valueOf(date.date.toString())
        }

        binding.calendarView.setOnMonthChangedListener { _, date ->
            binding.calendarView.removeDecorators()
            binding.calendarView.invalidateDecorators()

            // Decorators 추가
            selectedMonthDecorator = SelectedMonthDecorator(requireContext(), date.month)
            binding.calendarView.addDecorators(
                dayDecorator,
                todayDecorator,
                sundayDecorator,
                saturdayDecorator,
                selectedMonthDecorator
            )
        }

        binding.calendarView.selectedDate = CalendarDay.today()
        selectedDate = simpleDateFormat.format(Date.valueOf(CalendarDay.today().date.toString()))
    }

    private fun initNumberPicker() {
        binding.numberPickerHour.apply {
            setFormatter { value ->
                String.format("%02d", value)
            }
            setOnValueChangedListener { picker, _, _ ->
                hour = String.format("%02d", picker.value)
            }
        }

        binding.numberPickerMinute.apply {
            setFormatter { value ->
                String.format("%02d", value)
            }
            setOnValueChangedListener { picker, _, _ ->
                minute = String.format("%02d", picker.value)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.setDate(
            "$selectedDate | $hour:$minute"
        )
    }
}