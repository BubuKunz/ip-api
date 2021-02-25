package yan.zubritskiy.ipapi.ipgeodata.ui

import android.view.MenuItem
import androidx.core.view.isVisible
import com.ferfalk.simplesearchview.SimpleSearchView
import org.koin.androidx.viewmodel.ext.android.viewModel
import yan.zubritskiy.ipapi.R
import yan.zubritskiy.ipapi.coreui.BaseFragment
import yan.zubritskiy.ipapi.coreui.viewBinding
import yan.zubritskiy.ipapi.databinding.FragmentIpGeodataSearchBinding
import yan.zubritskiy.ipapi.ipgeodata.ui.viewmodel.IpGeoDataSearchViewModel

class IpGeoDataSearchFragment : BaseFragment(R.layout.fragment_ip_geodata_search) {

    private val binding by viewBinding(FragmentIpGeodataSearchBinding::bind)
    private val viewModel by viewModel<IpGeoDataSearchViewModel>()
    override fun setupViews() = with(binding) {
        toolbar.inflateMenu(R.menu.search_menu)
        val item: MenuItem = toolbar.menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                viewModel.search(p0)
                return true
            }

            override fun onQueryTextChange(p0: String): Boolean {
                // autogenerated
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                // autogenerated
                return false
            }
        })
    }

    override fun setupObservers() = with(viewModel) {
        handleBaseViewModelEvents(this)
        geoDataFormatted.observe(viewLifecycleOwner) {
            binding.resultText.text = it
        }
        searching.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
    }

    companion object {
        fun newInstance() = IpGeoDataSearchFragment()
    }
}