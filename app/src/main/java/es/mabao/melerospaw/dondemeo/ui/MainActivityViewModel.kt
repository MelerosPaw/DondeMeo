package es.mabao.melerospaw.dondemeo.ui

import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MainActivityViewModel: ViewModel() {

    var unexplored: List<LatLng>? = null
    var reviewed: List<LatLng>? = null
}