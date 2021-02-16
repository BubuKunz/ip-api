package yan.zubritskiy.ipapi.ipgeodata.network

import yan.zubritskiy.ipapi.core.Result

interface RemoteData {

    suspend fun getGeoData(ip: String, fields: List<String>?): Result<IpGeoData>
}