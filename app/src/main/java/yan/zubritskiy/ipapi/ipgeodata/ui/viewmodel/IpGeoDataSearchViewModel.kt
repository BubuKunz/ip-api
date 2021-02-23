package yan.zubritskiy.ipapi.ipgeodata.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yan.zubritskiy.ipapi.core.onSuccessNotNull
import yan.zubritskiy.ipapi.ipgeodata.repo.IpGeoDataRepository

class IpGeoDataSearchViewModel(
    private val repository: IpGeoDataRepository
) : ViewModel() {

    private val ipV4regex =
        "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$".toRegex()
    private val _geoDataFormatted = MutableLiveData<String>()
    val geoDataFormatted: LiveData<String> = _geoDataFormatted


    fun search(ip: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (ip.matches(ipV4regex)) {
                repository.getGeoData(ip)
                    .onSuccessNotNull {
                        withContext(Dispatchers.Main) {
                            _geoDataFormatted.value = it.asFormattedJson()
                        }
                    }
            }
        }
    }

    private fun Map<String, Any>.asFormattedJson(): String {
        val builder = StringBuilder()
        builder.append("{\n")
        onEachIndexed { index, entry ->
            builder.append("   \"${entry.key}\": ")
            when (entry.value) {
                is String -> builder.append("\"${entry.value}\"")
                else -> builder.append("${entry.value}")
            }
            if (index != size - 1) builder.append(",\n")
            else builder.append("\n")
        }
        builder.append("}")
        return builder.toString()
    }
}