package es.mabao.melerospaw.dondemeo.extensions

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.widget.Toast

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun kitKatApiLevel(executeIfKitKatOrHigher: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        executeIfKitKatOrHigher.invoke()
    }
}

fun Context.toPixels(dpi: Float) = dpi * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)