package yan.zubritskiy.ipapi.core

typealias ApiResult<T> = Result<T>
typealias ApiError = Result.Error
typealias ApiSuccess<T> = Result.Success<T>

sealed class Result<out R> {

    data class Success<out R>(val data: R?) : Result<R>()
    data class Error(val error: Throwable) : Result<Nothing>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other?.javaClass != javaClass) return false

            other as Error
            if (error.javaClass != other.error.javaClass) return false
            return error.message == other.error.message
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
        }
    }
}

inline fun <T, R> Result<T>.mapSuccessNotNull(map: (T) -> R): Result<R> {
    return when (this) {
        is ApiError -> this
        is ApiSuccess -> {
            if (data != null) ApiSuccess(map(data))
            else ApiError(IllegalArgumentException("unexpected empty body"))
        }
    }
}

inline fun <T> Result<T>.onAny(onAny: (Result<T>) -> Unit): Result<T> {
    onAny(this)
    return this
}

inline fun <T> Result<T>.onSuccessNotNull(onSuccess: (T) -> Unit): Result<T> {
    if (this is Result.Success && data != null) {
        onSuccess(data)
    }
    return this
}

abstract class BaseError(message: String = "") : Throwable(message)
