package yan.zubritskiy.ipapi.ipgeodata.repo

import yan.zubritskiy.ipapi.core.Result
import yan.zubritskiy.ipapi.ipgeodata.repo.model.IpGeoData

interface IpGeoDataRepository {

    /**
     * Returns Result.Success<IpGeoData> with all fields
     */
    suspend fun getGeoData(ip: String): Result<IpGeoData>
}