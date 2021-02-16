package yan.zubritskiy.ipapi.ipgeodata.network

import com.squareup.moshi.Json

data class IpGeoData(
    val query: String?,
    val status: String?,
    val continent: String?,
    val continentCode: String?,
    val country: String?,
    val countryCode: String?,
    val region: String?,
    val regionName: String?,
    val city: String?,
    val district: String?,
    val zip: String?,
    val lat: Double?,
    val lon: Double?,
    val timezone: String?,
    val offset: Double?,
    val currency: String?,
    val isp: String?,
    val org: String?,
    @Json(name = "as")
    val asValue: String?,
    @Json(name = "asname")
    val asName: String?,
    val reverse: String?,
    val mobile: Boolean?,
    val proxy: Boolean?,
    val hosting: Boolean?,
)