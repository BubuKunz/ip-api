package yan.zubritskiy.ipapi.core

typealias ApiResult<T> = Result<T>
typealias ApiError = Result.Error
typealias ApiSuccess<T> = Result.Success<T>

sealed class Result<out R> {

    data class Success<out R>(val data: R?) : Result<R>()
    data class Error(val error: BaseError) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
        }
    }
}



abstract class BaseError(message: String = "") : Throwable(message)
