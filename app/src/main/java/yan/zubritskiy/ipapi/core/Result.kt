package yan.zubritskiy.ipapi.core

typealias ApiResult<T> = Result<T>
typealias ApiError = Result.Error
typealias ApiSuccess<T> = Result.Success<T>

sealed class Result<out R> {

    data class Success<out R>(val data: R?) : Result<R>()
    data class Error(val error: Throwable) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
        }
    }
}

inline fun <T, R> Result<T>.mapOnSuccess(map: (T) -> R): Result<R> {
    return if (this is Result.Success) {
        Result.Success(this.data?.let { map(it) })
    } else this as Result<R>
}

abstract class BaseError(message: String = "") : Throwable(message)
