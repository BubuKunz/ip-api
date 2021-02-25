package yan.zubritskiy.ipapi.coreui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yan.zubritskiy.ipapi.core.ApiError
import yan.zubritskiy.ipapi.core.BaseError
import yan.zubritskiy.ipapi.core.Result
import yan.zubritskiy.ipapi.corenetwork.NetworkError

abstract class BaseViewModel : ViewModel() {

    protected val _onNetworkErrorEvent = SingleLiveEvent<Throwable>()
    val onNetworkErrorEvent: LiveData<Throwable> = _onNetworkErrorEvent

    protected suspend fun <T : Any> Result<T>.handleError(
        intercept: suspend ((Throwable) -> Boolean) = { false }
    ): Result<T> {
        if (this is ApiError && error is BaseError) {
            val intercepted = intercept(error)
            if (!intercepted) {
                when (error) {
                    is NetworkError.SuspendCancellationError -> {
                        // ignore
                    }
                }
                _onNetworkErrorEvent.postValue(error)
            }
        }
        return this
    }

    protected suspend fun <T> MutableLiveData<T>.setOnMain(v: T) {
        withContext(Dispatchers.Main) {
            value = v
        }
    }
}