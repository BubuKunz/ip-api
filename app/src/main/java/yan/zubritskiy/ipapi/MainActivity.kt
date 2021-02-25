package yan.zubritskiy.ipapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import yan.zubritskiy.ipapi.coreui.extension.addFragmentSafe
import yan.zubritskiy.ipapi.ipgeodata.ui.WelcomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(supportFragmentManager) {
            addFragmentSafe(
                create = { WelcomeFragment.newInstance() },
                container = R.id.mainContainer,
                addToBackStack = false
            )
        }
    }
}