package yan.zubritskiy.ipapi.ipgeodata.network

import yan.zubritskiy.ipapi.core.Logger
import yan.zubritskiy.ipapi.core.Result
import yan.zubritskiy.ipapi.core.map
import yan.zubritskiy.ipapi.core.mapOnSuccess
import yan.zubritskiy.ipapi.corenetwork.BaseNetworkDataSource
import yan.zubritskiy.ipapi.corenetwork.NetworkConnectionManager
import yan.zubritskiy.ipapi.ipgeodata.network.error.IpGeoDataSearchFail
import yan.zubritskiy.ipapi.ipgeodata.network.model.IpGeoData


class RemoteDataImpl(
    private val api: ApiService,
    logger: Logger,
    networkConnectionManager: NetworkConnectionManager
) : BaseNetworkDataSource(logger, networkConnectionManager), RemoteData {
    override suspend fun getGeoData(ip: String, fields: List<String>?): Result<IpGeoData> = safeResult {
        api.getGeoData(ip, fields = fields?.commaQueryFormat())
    }
        .map {
            if (it is Result.Success && it.data?.get("status") == "fail") Result.Error(
                IpGeoDataSearchFail(
                    it.data["message"] as? String
                )
            )
            else it
        }
        .mapOnSuccess {
            IpGeoData(
                query = it["query"] as? String,
                status = it["status"] as? String,
                continent = it["continent"] as? String,
                continentCode = it["continentCode"] as? String,
                country = it["country"] as? String,
                countryCode = it["countryCode"] as? String,
                region = it["region"] as? String,
                regionName = it["regionName"] as? String,
                city = it["city"] as? String,
                district = it["district"] as? String,
                zip = it["zip"] as? String,
                lat = it["lat"] as? Double,
                lon = it["lon"] as? Double,
                timezone = it["timezone"] as? String,
                offset = it["offset"] as? Double,
                currency = it["currency"] as? String,
                isp = it["isp"] as? String,
                org = it["org"] as? String,
                asValue = it["as"] as? String,
                asName = it["asname"] as? String,
                reverse = it["reverse"] as? String,
                mobile = it["mobile"] as? Boolean,
                proxy = it["proxy"] as? Boolean,
                hosting = it["hosting"] as? Boolean,
            )
        }
}