package yan.zubritskiy.ipapi.domain

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import yan.zubritskiy.ipapi.core.ApiError
import yan.zubritskiy.ipapi.core.ApiSuccess
import yan.zubritskiy.ipapi.corenetwork.NetworkError
import yan.zubritskiy.ipapi.ipgeodata.domain.SearchGeoDataUseCase
import yan.zubritskiy.ipapi.ipgeodata.repo.IpGeoDataRepository

class SearchGeoDataUseCaseTest {

    private val validationErrorResult = ApiError(IllegalArgumentException("invalid ip"))
    private val successResult = ApiSuccess<Map<String, Any>>(
        mapOf(
            "district" to "",
            "zip" to "61195",
            "lat" to 49.982,
            "hosting" to false
        )
    )
    private val expectedSuccessResult = ApiSuccess<String>(
        "{\n   \"district\": \"\",\n   \"zip\": \"61195\",\n   \"lat\": 49.982,\n   \"hosting\": false\n}"
    )
    private val data: Collection<Array<Any>> = listOf(
        // Valid ips
        arrayOf("204.120.0.15", expectedSuccessResult),
        arrayOf("192.168.1.50", expectedSuccessResult),
        arrayOf("192.168.1.1", expectedSuccessResult),
        //invalid ips
        arrayOf("101.5.40.1.2", validationErrorResult),
        arrayOf("101.5.40", validationErrorResult),
        arrayOf("101", validationErrorResult),
        arrayOf("101.5", validationErrorResult),
        arrayOf("anyString", validationErrorResult),
        arrayOf("", validationErrorResult),
    )
    private lateinit var repository: IpGeoDataRepository
    private lateinit var useCaseTest: SearchGeoDataUseCase

    @Before
    fun init() {
        repository = mockk {
            coEvery { getGeoData(any()) } returns successResult
        }
        useCaseTest = SearchGeoDataUseCase(repository)
    }

    @Test
    fun testIpValidationAndSuccessResult() = runBlocking {
        data.forEach {
            val result = useCaseTest.getFormattedGeoData(it[0] as String)
            assertEquals(it[1], result)
        }
    }

    @Test
    fun testTransmitErrorResult() = runBlocking {
        val expectedError = ApiError(NetworkError.ConnectionTimeout)
        val validIp = "204.120.0.15"
        coEvery { repository.getGeoData(validIp) } returns expectedError
        val result = useCaseTest.getFormattedGeoData(validIp)
        assertEquals(expectedError, result)
    }
}