package umc.everyones.lck.util.extension

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileOutputStream

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.snackBar(anchorView: View, message: () -> String) {
    Snackbar.make(anchorView, message(), Snackbar.LENGTH_SHORT).show()
}

fun Context.stringOf(@StringRes resId: Int) = getString(resId)

fun Context.colorOf(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

fun Context.drawableOf(@DrawableRes resId: Int) = ContextCompat.getDrawable(this, resId)

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}

fun Context.uriToFile(uri: Uri): File {
    val inputStream = contentResolver.openInputStream(uri) ?: return File("")
    val file = File(cacheDir, getFileName(uri))
    val outputStream = FileOutputStream(file)
    inputStream.copyTo(outputStream)
    inputStream.close()
    outputStream.close()
    return file
}

fun Context.getFileName(uri: Uri): String {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor.use {
            if (it != null && it.moveToFirst()) {
                result = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            }
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/') ?: -1
        if (cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result ?: "unknown"
}