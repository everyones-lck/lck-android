package umc.everyones.lck.util.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

// EditText 길이에 따른 유효성 검사 확장 함수
fun EditText.validateMaxLength(length: Int, onLengthExceeded: () -> Unit){
    this.addTextChangedListener(object : TextWatcher {
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
    })
}