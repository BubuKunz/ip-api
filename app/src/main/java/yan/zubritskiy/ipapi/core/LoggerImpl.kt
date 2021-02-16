package yan.zubritskiy.ipapi.core

import android.util.Log

private const val TAG = "Logger"

class LoggerImpl : Logger {
    override fun log(message: String) {
        Log.i(TAG, message)
    }

    override fun exception(exception: Throwable) {
        Log.e(TAG, exception.message, exception)
    }
}