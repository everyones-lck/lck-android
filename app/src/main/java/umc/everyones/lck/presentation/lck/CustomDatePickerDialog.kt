package umc.everyones.lck.presentation.lck

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.FrameLayout
import umc.everyones.lck.R

class CustomDatePickerDialog(
    context: Context,
    listener: OnDateSetListener,
    private val year: Int,
    private val month: Int,
    private val dayOfMonth: Int
) : DatePickerDialog(context,listener, year, month, dayOfMonth) {

    private var listener: OnDateSetListener = listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DatePickerDialog의 상단 헤더 영역을 숨김 처리
        try {
            val datePickerField = DatePickerDialog::class.java.getDeclaredField("mDatePicker")
            datePickerField.isAccessible = true
            val datePicker = datePickerField.get(this) as DatePicker
            val headerView = datePicker.findViewById<View>(
                Resources.getSystem().getIdentifier("date_picker_header", "id", "android")
            )
            headerView.visibility = View.GONE

            // 다이얼로그 위치 설정
            window?.let {
                val layoutParams = it.attributes
                layoutParams.gravity = Gravity.END
                layoutParams.x = 60
                layoutParams.y = -330
                it.attributes = layoutParams

                it.setBackgroundDrawableResource(android.R.color.transparent)
            }

            val buttonsLayout = findViewById<View>(
                Resources.getSystem().getIdentifier("android:id/buttonPanel", null, null)
            )
            buttonsLayout?.visibility = View.GONE

            // DatePicker 외부 클릭 시 다이얼로그 종료
            setCanceledOnTouchOutside(true)

            // DatePicker에서 날짜 선택 시 다이얼로그 종료
            datePicker.init(year, month, dayOfMonth) { _, selectedYear, selectedMonth, selectedDay ->
                listener.onDateSet(datePicker, selectedYear, selectedMonth, selectedDay)
                dismiss()
            }
            customizeDatePicker(datePicker)

            // 데이터 피커(배경 제외) 마진으로 위치 이동
            val parent = datePicker.parent as ViewGroup
            parent.removeView(datePicker)
            val wrapper = FrameLayout(context)
            wrapper.addView(datePicker)

            val balloonBackground: Drawable = context.getDrawable(R.drawable.bg_calendar_balloon)!!
            wrapper.background = balloonBackground

            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.topMargin = 100
            datePicker.layoutParams = layoutParams

            parent.addView(wrapper)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun customizeDatePicker(datePicker: DatePicker) {
        try {
            datePicker.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            val balloonBackground: Drawable = context.getDrawable(R.drawable.bg_calendar_balloon)!!
            datePicker.background = balloonBackground

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

