package yan.zubritskiy.ipapi.coreui.extension

import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import yan.zubritskiy.ipapi.coreui.MainFlowNavigator
import yan.zubritskiy.ipapi.coreui.Navigator
import yan.zubritskiy.ipapi.ipgeodata.ui.IpGeoDataSearchFragment
import yan.zubritskiy.ipapi.ipgeodata.ui.WelcomeFragment

fun Fragment.navigators(): Lazy<Navigator> {
    return when (this) {
        is WelcomeFragment, is IpGeoDataSearchFragment -> {
            inject<MainFlowNavigator> { parametersOf(this) }
        }
        else -> throw IllegalStateException("No navigator provided for fragment: $this")
    }
}