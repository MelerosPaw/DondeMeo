package es.mabao.melerospaw.dondemeo.extensions

import android.view.View
import android.view.ViewGroup

fun ViewGroup.forEachChildView(onEachView: (View) -> Unit) {
    for (i in 0 until childCount) {
        onEachView(getChildAt(i))
    }
}

inline fun <reified T: View?> ViewGroup.findFirstChild(condition: (View)-> Boolean): T? {
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        if (condition(child)) {
            return if (child is T) child else null
        }
    }
    return null
}

fun ViewGroup.getChildList(): List<View> {
    val childList = ArrayList<View>()
    forEachChildView { childList.add(it) }
    return childList
}