package yan.zubritskiy.ipapi.coreui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import yan.zubritskiy.ipapi.R
import yan.zubritskiy.ipapi.coreui.extension.addFragment
import yan.zubritskiy.ipapi.ipgeodata.ui.IpGeoDataSearchFragment

// in case of using Android Navigation Component constructor parameters need to be refactored
class MainFlowNavigator(
    private val currentFragment: Fragment
) : Navigator {
    override fun goTo(destination: NavDestination): Boolean {
        var handled = true
        when (destination) {
            NavDestination.IpGeoDataSearch -> addFragment(IpGeoDataSearchFragment.newInstance())
            else -> handled = false
        }
        return handled
    }

    private fun addFragment(fragment: Fragment) {
        currentFragment.activity?.supportFragmentManager?.addFragment(
            fragment = fragment,
            container = R.id.mainContainer
        )
    }
}