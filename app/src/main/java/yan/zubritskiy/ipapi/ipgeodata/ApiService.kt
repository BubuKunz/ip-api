package yan.zubritskiy.ipapi.ipgeodata

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "http://ip-api.com"
private const val IP = "ip"
private const val FIELDS = "fields"

interface ApiService {
    @GET("/json/{$IP}}")
    fun getGeoData(@Path(IP) ip: String, @Query(FIELDS) fields: List<String>?): Response<IpGeoData>
}