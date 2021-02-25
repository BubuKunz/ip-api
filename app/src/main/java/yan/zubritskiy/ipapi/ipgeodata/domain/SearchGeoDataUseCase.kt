package yan.zubritskiy.ipapi.ipgeodata.domain

import yan.zubritskiy.ipapi.core.ApiError
import yan.zubritskiy.ipapi.core.Result
import yan.zubritskiy.ipapi.core.mapSuccessNotNull
import yan.zubritskiy.ipapi.ipgeodata.repo.IpGeoDataRepository

class SearchGeoDataUseCase(
    private val repository: IpGeoDataRepository
) {

    private val ipV6regex = "\\A(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}\\z".toRegex()
    private val ipV4regex =
        "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$".toRegex()

    suspend fun getFormattedGeoData(ip: String): Result<String> {
        return if (ip.matches(ipV4regex) || ip.matches(ipV6regex)) {
            repository.getGeoData(ip)
                .mapSuccessNotNull {
                    it.asFormattedJson()
                }
        } else {
            ApiError(IllegalArgumentException("invalid ip"))
        }
    }

    private fun Map<String, Any>.asFormattedJson(): String {
        val builder = StringBuilder()
        builder.append("{\n")
        onEachIndexed { index, entry ->
            builder.append("   \"${entry.key}\": ")
            when (entry.value) {
                is String -> builder.append("\"${entry.value}\"")
                else -> builder.append("${entry.value}")
            }
            if (index != size - 1) builder.append(",\n")
            else builder.append("\n")
        }
        builder.append("}")
        return builder.toString()
    }
}