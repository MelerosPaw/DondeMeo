package es.mabao.melerospaw.dondemeo.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import es.mabao.melerospaw.dondemeo.R

fun hasPermission(context: Context, vararg permissions: String) =
        permissions.none { !hasPermission(context, it) }

fun hasPermission(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

fun showPermissionRationale(context: Context,
                            title:String = context.getString(R.string.permissions__permission_denied),
                            message:String = context.getString(R.string.permissions__location_rationale_message),
                            positiveText: String = context.getString(R.string.permissions__im_sure),
                            negativeText: String = context.getString(R.string.permissions__change),
                            callback: RationaleCallback) {
    AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveText) { _, _ -> callback.onDontChangeAnswer() }
            .setNegativeButton(negativeText) { _, _ -> callback.onChangeAnswer() }
            .setCancelable(false)
            .create()
            .show()

}

@RequiresApi(Build.VERSION_CODES.M)
fun processResult(activity: Activity, grantResults: IntArray, vararg permissions: String,
                  callback: ResultCallback) {
    val mustShowRationale = mustShowRationaleDialog(activity)
    if (areAllPermissionsGranted(grantResults) || !mustShowRationaleDialog(activity, *permissions)) {
        callback.proceed()
    } else if must{
        // TODO 16/10/2018 
    } else {
        
    }
}

private fun areAllPermissionsGranted(grantResults: IntArray) =
        grantResults[0] == PackageManager.PERMISSION_GRANTED

@RequiresApi(Build.VERSION_CODES.M)
private fun mustShowRationaleDialog(activity: Activity, vararg permissions: String) =
        permissions.any {activity.shouldShowRequestPermissionRationale(it) }


interface RationaleCallback {
    fun onChangeAnswer()
    fun onDontChangeAnswer()
}

interface ResultCallback {
    fun proceed()
    fun mustShowRationale()
    fun dontProceed()
}