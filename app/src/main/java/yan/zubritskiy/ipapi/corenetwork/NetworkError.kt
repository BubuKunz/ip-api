package yan.zubritskiy.ipapi.corenetwork

import yan.zubritskiy.ipapi.core.BaseError

private const val SUCCESS_NO_CONTENT = "No Content"
private const val ERROR_BAD_REQUEST = "Bad Request"
private const val ERROR_USAGE_LIMIT = "Requests limit exceeded. Try again in a minute"
private const val ERROR_CONNECTION = "Connection Error"
private const val ERROR_CONNECTION_TIMEOUT = "Connection Timeout"
private const val ERROR_SERVER_INTERNAL = "Something went wrong"
private const val ERROR_SERVER_TEMPORARY_UNAVAILABLE = "Server is temporarily unavailable."
private const val ERROR_SERVER_MAINTENANCE = "Technical works are in progress."

sealed class NetworkError(errorMessage: String) : BaseError(errorMessage) {
    object NoContent : NetworkError(SUCCESS_NO_CONTENT)
    object BadRequest : NetworkError(ERROR_BAD_REQUEST)
    object SuspendCancellationError : NetworkError("")
    object UsageLimit : NetworkError(ERROR_USAGE_LIMIT)
    object Connection : NetworkError(ERROR_CONNECTION)
    object ConnectionTimeout : NetworkError(ERROR_CONNECTION_TIMEOUT)
    object ServerInternalError : NetworkError(ERROR_SERVER_INTERNAL)
    object ServerTemporaryUnavailable : NetworkError(ERROR_SERVER_TEMPORARY_UNAVAILABLE)
    object ServerMaintenance : NetworkError(ERROR_SERVER_MAINTENANCE)
    data class OperationCode(val opCode: String, val responseCode: Int) : NetworkError(opCode)
    data class Unknown(val errorMessage: String = "") : NetworkError(errorMessage)
}