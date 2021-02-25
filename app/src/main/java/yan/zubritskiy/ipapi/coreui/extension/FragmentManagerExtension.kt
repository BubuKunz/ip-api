package yan.zubritskiy.ipapi.coreui.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.addFragment(
    @IdRes container: Int,
    fragment: Fragment,
    tag: String = fragment::class.java.name,
    addToBackStack: Boolean = true
) {
    beginTransaction()
        .add(container, fragment, tag)
        .apply {
            if (addToBackStack) addToBackStack(tag)
        }
        .commit()
}

inline fun <reified T : Fragment> FragmentManager.addFragmentSafe(
    @IdRes container: Int,
    create: () -> T,
    tag: String = T::class.java.name,
    addToBackStack: Boolean = true
): Fragment {
    val tagFragment = findFragmentByTag(tag)
    if (tagFragment != null) {
        return tagFragment
    }
    val newFragment = create()
    addFragment(container, newFragment, tag, addToBackStack)
    return newFragment
}

inline fun <reified T : Fragment> FragmentManager.findByTag(tag: String = T::class.java.name): T? =
    findFragmentByTag(tag) as? T

val FragmentManager.topFragment: Fragment?
    get() = if (fragments.size > 0) fragments[fragments.size - 1] else null