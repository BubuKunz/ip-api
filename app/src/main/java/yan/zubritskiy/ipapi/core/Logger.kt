package yan.zubritskiy.ipapi.core

interface Logger {
    fun log(message: String)
    fun exception(exception: Throwable)
}