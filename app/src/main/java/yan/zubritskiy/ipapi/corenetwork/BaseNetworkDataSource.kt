package yan.zubritskiy.ipapi.corenetwork

import kotlinx.coroutines.CancellationException
import retrofit2.Response
import yan.zubritskiy.ipapi.core.ApiError
import yan.zubritskiy.ipapi.core.ApiResult
import yan.zubritskiy.ipapi.core.ApiSuccess
import yan.zubritskiy.ipapi.core.Logger
import yan.zubritskiy.ipapi.corenetwork.HttpStatusCode.CODE_BAD_REQUEST
import yan.zubritskiy.ipapi.corenetwork.HttpStatusCode.CODE_SERVER_INTERNAL_ERROR
import yan.zubritskiy.ipapi.corenetwork.HttpStatusCode.CODE_SERVER_MAINTENANCE
import yan.zubritskiy.ipapi.corenetwork.HttpStatusCode.CODE_SERVER_UNAVAILABLE
import yan.zubritskiy.ipapi.corenetwork.HttpStatusCode.USAGE_LIMIT
import java.net.SocketTimeoutException

abstract class BaseNetworkDataSource(
    val logger: Logger,
    protected val networkConnectionManager: NetworkConnectionManager
) {

    protected inline fun <T> safeResult(call: () -> Response<T>): ApiResult<T> {
        return safeResultNullable(call).notNullOrError()
    }

    protected inline fun <T> safeResultNullable(call: () -> Response<T>): ApiResult<T?> {
        return try {
            return when {
                networkConnectionManager.isNetworkAvailable() -> {
                    val response = call.invoke()
                    if (response.isSuccessful) {
                        val data = response.body()
                        ApiSuccess(data)
                    } else {
                        when (response.code()) {
                            CODE_BAD_REQUEST -> ApiError(NetworkError.BadRequest)
                            CODE_SERVER_INTERNAL_ERROR -> ApiError(NetworkError.ServerInternalError)
                            CODE_SERVER_UNAVAILABLE -> ApiError(NetworkError.ServerTemporaryUnavailable)
                            CODE_SERVER_MAINTENANCE -> ApiError(NetworkError.ServerMaintenance)
                            USAGE_LIMIT -> ApiError(NetworkError.UsageLimit)
                            else -> ApiError(NetworkError.Unknown(response.code().toString()))
                        }
                    }
                }
                else -> ApiError(NetworkError.Connection)
            }
        } catch (se: SocketTimeoutException) {
            logger.exception(se)
            ApiError(NetworkError.ConnectionTimeout)
        } catch (se: CancellationException) {
            logger.exception(se)
            ApiError(NetworkError.SuspendCancellationError)
        } catch (e: Throwable) {
            logger.exception(e)
            ApiError(NetworkError.Unknown(e.message ?: ""))
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> ApiResult<T?>.notNullOrError(): ApiResult<T> {
        return if (this is ApiSuccess<*> && data != null)
            this as ApiSuccess<T>
        else this as ApiError
    }
}