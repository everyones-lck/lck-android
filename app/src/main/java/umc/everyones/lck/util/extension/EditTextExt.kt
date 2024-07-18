package umc.everyones.lck.util.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.validateMaxLength(maxLength: Int, func: () -> Unit){
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s != null){
                if(s.length >= maxLength){
                    func()
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }
    })
}