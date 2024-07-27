package umc.everyones.lck.util.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.text.DecimalFormat

// EditText 길이에 따른 유효성 검사 확장 함수
fun EditText.validateMaxLength(lifecycleOwner: LifecycleOwner, length: Int, onLengthExceeded: () -> Unit){
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s != null){
                if(s.length >= length){
                    onLengthExceeded()
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    this.addTextChangedListener(textWatcher)

    lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            this@validateMaxLength.removeTextChangedListener(textWatcher)
        }
    })
}

fun EditText.addDecimalFormattedTextWatcher(
    lifecycleOwner: LifecycleOwner
) {
    var result = ""
    val decimalFormat = DecimalFormat("#,###")

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val text = s.toString()
            if (text.isNotEmpty() && text != result) {
                result = decimalFormat.format(text.replace(",", "").toDouble())
                setText(result)
                setSelection(result.length)
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    this.addTextChangedListener(textWatcher)

    lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            this@addDecimalFormattedTextWatcher.removeTextChangedListener(textWatcher)
        }
    })
}