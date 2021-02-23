package yan.zubritskiy.ipapi.coreui

import androidx.fragment.app.Fragment

interface Navigator {

    /**
     * returns false if navigation failed
     */
    fun goTo(destination: NavDestination): Boolean
}