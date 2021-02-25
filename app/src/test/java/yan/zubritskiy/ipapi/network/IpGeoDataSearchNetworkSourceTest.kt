package yan.zubritskiy.ipapi.network

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response
import yan.zubritskiy.ipapi.ipgeodata.network.ApiService
import yan.zubritskiy.ipapi.ipgeodata.network.RemoteDataImpl

class IpGeoDataSearchNetworkSourceTest {

    @Test
    fun testWrappedSafeResult() = runBlocking(Dispatchers.IO) {
        // todo
    }

    @Test
    fun testInvokeApi() = runBlocking(Dispatchers.IO) {
        val service: ApiService = mockk {
            coEvery { getGeoData(any(), any()) } returns Response.success(emptyMap())
        }
        val remoteData = RemoteDataImpl(
            service,
            mockk(),
            mockk {
                every { isNetworkAvailable() } returns true
            }
        )
        remoteData.getGeoData("ip", listOf("lat", "lng"))
        coVerify {
            service.getGeoData("ip", "lat,lng")
        }
    }
}