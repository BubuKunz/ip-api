package yan.zubritskiy.ipapi.ipgeodata.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import yan.zubritskiy.ipapi.core.onAny
import yan.zubritskiy.ipapi.core.onSuccessNotNull
import yan.zubritskiy.ipapi.coreui.BaseViewModel
import yan.zubritskiy.ipapi.ipgeodata.domain.SearchGeoDataUseCase

class IpGeoDataSearchViewModel(
    private val useCase: SearchGeoDataUseCase
) : BaseViewModel() {
    private val _geoDataFormatted = MutableLiveData<String>()
    val geoDataFormatted: LiveData<String> = _geoDataFormatted
    private val _searching = MutableLiveData<Boolean>()
    val searching: LiveData<Boolean> = _searching

    fun search(ip: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searching.setOnMain(true)
            useCase.getFormattedGeoData(ip)
                .handleError()
                .onSuccessNotNull { _geoDataFormatted.setOnMain(it) }
                .onAny { _searching.setOnMain(false) }
        }
    }
}