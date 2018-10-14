package es.mabao.melerospaw.dondemeo.extensions

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

inline fun <reified T: Fragment> FragmentManager.getFragment(tag: String): T? {
    return findFragmentByTag(tag) as T?
}