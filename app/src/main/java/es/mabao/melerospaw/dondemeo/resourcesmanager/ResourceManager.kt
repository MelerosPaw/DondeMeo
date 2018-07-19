package es.mabao.melerospaw.dondemeo.resourcesmanager

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat

class ResourceManager {

    companion object {
        var sContext: Context? = null

        fun init(context: Context) {
            sContext = context
        }

        fun getColor(@ColorRes colorId: Int) = ContextCompat.getColor(
                sContext?: throw IllegalStateException("ResourceManger hasn't been initialized yet!"),
                colorId)
    }
}