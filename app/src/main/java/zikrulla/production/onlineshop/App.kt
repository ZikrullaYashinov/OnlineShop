package zikrulla.production.onlineshop

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application(){

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build();
        Lingver.init(this, "en")
    }
}