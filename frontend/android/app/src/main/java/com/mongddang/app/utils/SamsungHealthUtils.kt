package com.mongddang.app.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.samsung.android.sdk.health.data.error.ResolvablePlatformException
import kotlinx.coroutines.CoroutineExceptionHandler
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun getExceptionHandler(
    activity: Activity,
    exceptionResponse: MutableLiveData<String>
): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, exception ->
        if ((exception is ResolvablePlatformException) && exception.hasResolution) {
            exception.resolve(activity)
        }
        exceptionResponse.postValue(exception.message!!)
    }
}

fun formatString(input: Float): String {
    return String.format(Locale.ENGLISH, "%.2f", input)
}

val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy (E)")
    .withZone(ZoneId.systemDefault())




