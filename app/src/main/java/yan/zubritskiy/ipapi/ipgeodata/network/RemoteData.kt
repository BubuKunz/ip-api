package yan.zubritskiy.ipapi.ipgeodata.network

import yan.zubritskiy.ipapi.core.Result
import yan.zubritskiy.ipapi.ipgeodata.network.model.IpGeoData

interface RemoteData {

    suspend fun getGeoData(ip: String, fields: List<String>?): Result<IpGeoData>
}