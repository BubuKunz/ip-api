package yan.zubritskiy.ipapi.ipgeodata.repo

import yan.zubritskiy.ipapi.core.Result
import yan.zubritskiy.ipapi.ipgeodata.network.RemoteData

private val allFields = listOf(
    "status",
    "message",
    "continent",
    "continentCode",
    "country",
    "countryCode",
    "region",
    "regionName",
    "city",
    "district",
    "zip",
    "lat",
    "lon",
    "timezone",
    "offset",
    "currency",
    "isp",
    "org",
    "as",
    "asname",
    "reverse",
    "mobile",
    "proxy",
    "hosting",
    "query"
)

class IpGeoDataRepositoryImpl(
    private val remoteData: RemoteData
) : IpGeoDataRepository {
    override suspend fun getGeoData(ip: String): Result<Map<String, Any>> = remoteData.getGeoData(ip, allFields)
}