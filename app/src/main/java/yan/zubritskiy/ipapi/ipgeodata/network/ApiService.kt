package yan.zubritskiy.ipapi.ipgeodata.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import yan.zubritskiy.ipapi.ipgeodata.network.model.IpGeoData

private const val IP = "ip"
private const val FIELDS = "fields"

interface ApiService {
    @GET("/json/{$IP}")
    suspend fun getGeoData(@Path(IP) ip: String, @Query(FIELDS) fields: String?): Response<Map<String, Any>>
}