package es.mabao.melerospaw.dondemeo

import android.app.Application
import es.mabao.melerospaw.dondemeo.resourcesmanager.ResourceManager

class DondeMeoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ResourceManager.init(this)
    }
}