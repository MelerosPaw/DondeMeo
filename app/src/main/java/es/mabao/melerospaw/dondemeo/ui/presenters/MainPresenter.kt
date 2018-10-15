package es.mabao.melerospaw.dondemeo.ui.presenters

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import es.mabao.melerospaw.dondemeo.ui.presenters.contracts.MainContract
import es.mabao.melerospaw.dondemeo.ui.presenters.contracts.MainContract.Presenter

class MainPresenter(override var view: MainContract.View) : Presenter {

    override fun fetchUnexploredPoints() {
        view.onUnexploredPointsRetrieved(listOf(LatLng(-45.0, 52.0), LatLng(-24.0, 67.0), LatLng(-2.0, 231.0)))
    }

    override fun fetchReviewedPoints() {
        view.onReviewedPointsRetrieved(listOf(LatLng(-37.0, 152.0), LatLng(-74.0, 11.0), LatLng(-4.0, 1.0)))
    }

    override fun onMarkerClicked(marker: Marker) {
        view.openPeePointDetail(marker)
    }

    override fun onEmptySpaceLongClicked(point: LatLng) {
        view.openNewPeePoint(point)
    }
}