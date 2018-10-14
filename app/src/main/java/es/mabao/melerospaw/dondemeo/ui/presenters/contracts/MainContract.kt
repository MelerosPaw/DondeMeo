package es.mabao.melerospaw.dondemeo.ui.presenters.contracts

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

interface MainContract {

    interface View {
        fun onUnexploredPointsRetrieved(positions: List<LatLng>)
        fun onReviewedPointsRetrieved(positions: List<LatLng>)
        fun showHistory()
        fun showInfoWindowForMarker(marker: Marker)
        fun openPeePointDetail(position: LatLng)
    }

    interface Presenter {

        var view: MainContract.View

        fun fetchUnexploredPoints()
        fun fetchReviewedPoints()
        fun onMarkerClicked(marker: Marker)
        fun removePoints(points: List<LatLng>?)
    }

}