package yan.zubritskiy.ipapi.coreui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes private val layoutId: Int) : Fragment(layoutId) {

    open fun setupViews() {}
    open fun setupObservers() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    protected inline fun handleBaseViewModelEvents(
        vararg viewModels: BaseViewModel,
        crossinline custom: (Throwable) -> Boolean = { false }
    ) {
        viewModels.forEach { vm ->
            vm.onNetworkErrorEvent.observe(viewLifecycleOwner) { exception ->
                exception.handleError(custom)
            }
        }
    }

    protected inline fun Throwable.handleError(custom: (Throwable) -> Boolean = { false }) {
        if (!custom(this)) {
            if (context != null && !message.isNullOrBlank()) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}