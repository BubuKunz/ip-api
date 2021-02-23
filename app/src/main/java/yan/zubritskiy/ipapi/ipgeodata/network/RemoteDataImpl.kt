package yan.zubritskiy.ipapi.ipgeodata.network

import yan.zubritskiy.ipapi.core.Logger
import yan.zubritskiy.ipapi.core.Result
import yan.zubritskiy.ipapi.corenetwork.BaseNetworkDataSource
import yan.zubritskiy.ipapi.corenetwork.NetworkConnectionManager


class RemoteDataImpl(
    private val api: ApiService,
    logger: Logger,
    networkConnectionManager: NetworkConnectionManager
) : BaseNetworkDataSource(logger, networkConnectionManager), RemoteData {
    override suspend fun getGeoData(ip: String, fields: List<String>?): Result<Map<String, Any>> = safeResult {
        api.getGeoData(ip, fields = fields?.commaQueryFormat())
    }
}