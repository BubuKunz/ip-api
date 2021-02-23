package yan.zubritskiy.ipapi.ipgeodata.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yan.zubritskiy.ipapi.core.mapOnSuccess
import yan.zubritskiy.ipapi.core.onSuccessNotNull
import yan.zubritskiy.ipapi.ipgeodata.repo.IpGeoDataRepository
import yan.zubritskiy.ipapi.ipgeodata.ui.mapper.GeoDataMapper
import yan.zubritskiy.ipapi.ipgeodata.ui.model.IpGeoData

class IpGeoDataSearchViewModel(
    private val repository: IpGeoDataRepository,
    private val mapper: GeoDataMapper
) : ViewModel() {

    private val ipV4regex =
        "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$".toRegex()
    private val _geoData = MutableLiveData<IpGeoData>()
    val geoData: LiveData<IpGeoData> = _geoData


    fun search(ip: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (ip.matches(ipV4regex)) {
                repository.getGeoData(ip)
                    .mapOnSuccess(mapper::from)
                    .onSuccessNotNull {
                       withContext(Dispatchers.Main) {
                           _geoData.value = it
                       }
                    }
            }
        }
    }
}