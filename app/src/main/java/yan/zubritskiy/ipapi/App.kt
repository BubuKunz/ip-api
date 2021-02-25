package yan.zubritskiy.ipapi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import yan.zubritskiy.ipapi.di.coreModule
import yan.zubritskiy.ipapi.di.coreNetworkModule
import yan.zubritskiy.ipapi.di.coreUiModule
import yan.zubritskiy.ipapi.di.ipGeoDataModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(coreModule, coreUiModule, coreNetworkModule, ipGeoDataModule)
        }
    }
}