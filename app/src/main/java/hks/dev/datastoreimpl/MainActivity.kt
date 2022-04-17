package hks.dev.datastoreimpl

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import hks.dev.datastoreimpl.ui.main.MainFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

//    val dataStoreImpl = DataStoreImpl(applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        lifecycleScope.launch {
//            Log.d("TAG", "onCreate1: ${dataStoreImpl.isNightMode().first()}")
//            dataStoreImpl.toggleNightMode()
//            Log.d("TAG", "onCreate2: ${dataStoreImpl.isNightMode().first()}")
        }

    }

}