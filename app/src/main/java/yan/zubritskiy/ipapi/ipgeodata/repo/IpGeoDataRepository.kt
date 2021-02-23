package yan.zubritskiy.ipapi.ipgeodata.repo

import yan.zubritskiy.ipapi.core.Result

interface IpGeoDataRepository {

    /**
     * Returns Result.Success<IpGeoData> with all fields
     */
    suspend fun getGeoData(ip: String): Result<Map<String, Any>>
}