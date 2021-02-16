package yan.zubritskiy.ipapi.ipgeodata.repo

import yan.zubritskiy.ipapi.core.Result
import yan.zubritskiy.ipapi.core.mapOnSuccess
import yan.zubritskiy.ipapi.ipgeodata.network.RemoteData
import yan.zubritskiy.ipapi.ipgeodata.repo.mapper.GeoDataMapper
import yan.zubritskiy.ipapi.ipgeodata.repo.model.IpGeoData

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
    private val remoteData: RemoteData,
    private val mapper: GeoDataMapper
) : IpGeoDataRepository {
    override suspend fun getGeoData(ip: String): Result<IpGeoData> = remoteData.getGeoData(ip, allFields).mapOnSuccess {
        mapper.from(it)
    }
}