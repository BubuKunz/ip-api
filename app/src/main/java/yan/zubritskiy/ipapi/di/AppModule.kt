package yan.zubritskiy.ipapi.di

import androidx.fragment.app.Fragment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import yan.zubritskiy.ipapi.BuildConfig
import yan.zubritskiy.ipapi.core.Logger
import yan.zubritskiy.ipapi.core.LoggerImpl
import yan.zubritskiy.ipapi.corenetwork.NetworkConnectionManager
import yan.zubritskiy.ipapi.corenetwork.NetworkConnectionManagerImpl
import yan.zubritskiy.ipapi.coreui.MainFlowNavigator
import yan.zubritskiy.ipapi.ipgeodata.network.ApiService
import yan.zubritskiy.ipapi.ipgeodata.network.RemoteData
import yan.zubritskiy.ipapi.ipgeodata.network.RemoteDataImpl
import yan.zubritskiy.ipapi.ipgeodata.repo.IpGeoDataRepository
import yan.zubritskiy.ipapi.ipgeodata.repo.IpGeoDataRepositoryImpl
import yan.zubritskiy.ipapi.ipgeodata.ui.viewmodel.IpGeoDataSearchViewModel
import yan.zubritskiy.ipapi.ipgeodata.repo.mapper.GeoDataMapper as RepoMapper
import yan.zubritskiy.ipapi.ipgeodata.ui.mapper.GeoDataMapper as UiMapper

val coreUiModule = module {
    factory { (f: Fragment) -> MainFlowNavigator(f) }
}

val coreModule = module {
    factory<Logger> { LoggerImpl() }
}

val coreNetworkModule = module {
    factory {
        OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY);
                        }
                    )
                }
            }
    }

    factory<Retrofit> {
        Retrofit.Builder().apply {
            baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(get<OkHttpClient.Builder>().build())

        }.build()
    }
    factory<NetworkConnectionManager> { NetworkConnectionManagerImpl(androidApplication()) }
}

val ipGeoDataModule = module {
    factory<ApiService> { createService() }
    factory<RemoteData> { RemoteDataImpl(get(), get(), get()) }
    factory { RepoMapper() }
    factory { UiMapper() }
    single<IpGeoDataRepository> { IpGeoDataRepositoryImpl(get(), get()) }
    viewModel { IpGeoDataSearchViewModel(get(), get()) }
}

private inline fun <reified T : Any> Scope.createService(): T {
    return get<Retrofit>().create(T::class.java)
}