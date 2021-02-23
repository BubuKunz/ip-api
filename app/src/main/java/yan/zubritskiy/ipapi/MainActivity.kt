package yan.zubritskiy.ipapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import yan.zubritskiy.ipapi.coreui.extension.addFragment
import yan.zubritskiy.ipapi.coreui.extension.findByTag
import yan.zubritskiy.ipapi.coreui.extension.topFragment
import yan.zubritskiy.ipapi.di.coreModule
import yan.zubritskiy.ipapi.di.coreNetworkModule
import yan.zubritskiy.ipapi.di.coreUiModule
import yan.zubritskiy.ipapi.di.ipGeoDataModule
import yan.zubritskiy.ipapi.ipgeodata.ui.WelcomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(coreModule, coreUiModule, coreNetworkModule, ipGeoDataModule)
        }
        with(supportFragmentManager) {
            addFragment(
                fragment = WelcomeFragment.newInstance(),
                container = R.id.mainContainer,
                addToBackStack = false
            )
        }
    }
}