package yan.zubritskiy.ipapi.ipgeodata.ui

import yan.zubritskiy.ipapi.R
import yan.zubritskiy.ipapi.coreui.BaseFragment
import yan.zubritskiy.ipapi.coreui.NavDestination
import yan.zubritskiy.ipapi.coreui.extension.navigators
import yan.zubritskiy.ipapi.coreui.viewBinding
import yan.zubritskiy.ipapi.databinding.FragmentWlecomeBinding

class WelcomeFragment : BaseFragment(R.layout.fragment_wlecome) {

    private val binding by viewBinding(FragmentWlecomeBinding::bind)
    private val navigator by navigators()

    override fun setupViews() = with(binding) {
        goToSearchBtn.setOnClickListener {
            navigator.goTo(NavDestination.IpGeoDataSearch)
        }
    }

    companion object {
        fun newInstance() = WelcomeFragment()
    }
}