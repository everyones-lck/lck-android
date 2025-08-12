package umc.everyones.lck.util.extension

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import umc.everyones.lck.R

fun Context.showCustomToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    val inflater = LayoutInflater.from(this)
    val layout = inflater.inflate(R.layout.custom_toast, null)

    val textView = layout.findViewById<TextView>(R.id.tv_toast_message)
    textView.text = message

    with(Toast(applicationContext)) {
        this.duration = duration
        view = layout
        show()
    }
}