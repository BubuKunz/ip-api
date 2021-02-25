package yan.zubritskiy.ipapi.repo

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import yan.zubritskiy.ipapi.core.ApiError
import yan.zubritskiy.ipapi.core.ApiSuccess
import yan.zubritskiy.ipapi.ipgeodata.network.RemoteData
import yan.zubritskiy.ipapi.ipgeodata.repo.IpGeoDataRepositoryImpl

class IpGeoDataRepositoryTest {
    private lateinit var repo: IpGeoDataRepositoryImpl
    private lateinit var remoteData: RemoteData
    private val defaultParams = listOf(
        "status",
        "message",
        "continent",
        "continentCode",
        "country",
        "countryCode",
        "region",
        "regionName",
        "city",
        "district",
        "zip",
        "lat",
        "lon",
        "timezone",
        "offset",
        "currency",
        "isp",
        "org",
        "as",
        "asname",
        "reverse",
        "mobile",
        "proxy",
        "hosting",
        "query"
    )

    @Before
    fun init() {
        remoteData = mockk<RemoteData>()
        repo = IpGeoDataRepositoryImpl(remoteData)
    }

    @Test
    fun remoteSourceInvokedSuccessTest() {
        runBlocking(Dispatchers.IO) {
            val expectedResult = ApiSuccess<Map<String, Any>>(emptyMap())
            coEvery { remoteData.getGeoData(any(), any()) } returns expectedResult
            val result = repo.getGeoData("someIp")
            coEvery {
                remoteData.getGeoData("someIp", defaultParams)
            }
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun remoteSourceInvokedErrorTest() {
        runBlocking(Dispatchers.IO) {
            val expectedResult = ApiError(Throwable())
            coEvery { remoteData.getGeoData(any(), any()) } returns expectedResult
            val result = repo.getGeoData("someIp")
            coEvery {
                remoteData.getGeoData("someIp", defaultParams)
            }
            assertEquals(expectedResult, result)
        }
    }
}