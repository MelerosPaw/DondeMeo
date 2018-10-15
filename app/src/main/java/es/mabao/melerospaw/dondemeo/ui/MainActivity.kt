package es.mabao.melerospaw.dondemeo.ui

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import es.mabao.melerospaw.dondemeo.R
import es.mabao.melerospaw.dondemeo.base.BaseActivity
import es.mabao.melerospaw.dondemeo.extensions.toast
import es.mabao.melerospaw.dondemeo.ui.presenters.MainPresenter
import es.mabao.melerospaw.dondemeo.ui.presenters.contracts.MainContract
import es.mabao.melerospaw.dondemeo.utils.RationaleCallback
import es.mabao.melerospaw.dondemeo.utils.hasPermission
import es.mabao.melerospaw.dondemeo.utils.processResult
import es.mabao.melerospaw.dondemeo.utils.showPermissionRationale
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), MainContract.View, OnMapReadyCallback {

    companion object {

        val REQUEST_CODE_LOCATION = 1
        val ADD_PP_ZOOM_THRESHOLD = 16F

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private lateinit var presenter: MainContract.Presenter
    private var googleMap: GoogleMap? = null
    private lateinit var viewModel: MainActivityViewModel
    private var addModeEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        loadView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == NewPeePointActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                displayNewPoint(data)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            processResult(this, grantResults, *permissions)
            onAddButtonClicked() // TODO 16/10/2018 
            showRationaleDialog()
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onUnexploredPointsRetrieved(positions: List<LatLng>) {
        viewModel.unexplored = positions
        drawPoints(positions)
    }

    override fun onReviewedPointsRetrieved(positions: List<LatLng>) {
        viewModel.reviewed = positions
        drawPoints(positions)
    }

    override fun showHistory() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showInfoWindowForMarker(marker: Marker) {
        marker.showInfoWindow()
    }

    override fun openPeePointDetail(marker: Marker) {
        toast(marker.title)
    }

    override fun openNewPeePoint(point: LatLng) {
        startActivityForResult(NewPeePointActivity.intentWithPosition(this, point),
                NewPeePointActivity.REQUEST_CODE)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        setUpMap(googleMap)
    }

    private fun loadView() {
        loadMap()
        setUpAddButton()
        setUpBottomBar()
    }

    private fun setUpAddButton() {
        main__btn__add.setOnClickListener {
            if (hasLocationPermission() || addModeEnabled) {
                onAddButtonClicked()
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestLocationPermission()
            }
        }
    }

    private fun onAddButtonClicked() {
        addModeEnabled = !addModeEnabled
        checkAddMode()
    }

    private fun setUpBottomBar() {
        main__bar__bottom_navigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.main__menu_item__show_unexplored -> { loadUnexplored() }
                R.id.main__menu_item__reviewed -> { loadReviewed() }
                R.id.main__menu_item__history -> { true }
                else -> false
            }
        }
    }

    private fun checkAddMode() {
        if (addModeEnabled && isMapReady()) {
            val currentTilt = googleMap!!.cameraPosition.tilt
            val currentBearing = googleMap!!.cameraPosition.bearing
            val position = if (hasLocationPermission()) { LatLng(-33.0, 35.0) } else { LatLng(-53.0, 15.0) }
            val cameraPosition = CameraUpdateFactory.newCameraPosition(
                    CameraPosition(position, 10F, currentTilt, currentBearing))
            googleMap?.animateCamera(cameraPosition)
        }
    }

    private fun loadUnexplored(): Boolean {
        if (viewModel.unexplored != null) {
            drawPoints(viewModel.unexplored!!)
        } else {
            presenter.fetchUnexploredPoints()
        }
        return true
    }

    private fun loadReviewed(): Boolean {
        if (viewModel.reviewed != null) {
            drawPoints(viewModel.reviewed!!)
        } else {
            presenter.fetchReviewedPoints()
        }
        return true
    }

    private fun loadMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setUpMap(googleMap: GoogleMap) {
        googleMap.setOnMarkerClickListener { marker ->
            presenter.onMarkerClicked(marker)
            true
        }
        googleMap.setOnMapLongClickListener { point ->
            if (googleMap.cameraPosition.zoom > ADD_PP_ZOOM_THRESHOLD) {
                presenter.onEmptySpaceLongClicked(point)
            } else {
                toast("Sé un poco más preciso. Haz más zoom en el mapa.")
            }
        }
        checkAddMode()
    }

    private fun setMarker(position: LatLng) {
        googleMap?.addMarker(MarkerOptions()
                .position(position)
                .title("${position.latitude}, ${position.longitude}"))
    }

    private fun drawPoints(positions: List<LatLng>) {
        if (isMapReady()) {
            googleMap?.clear()
            for (position: LatLng in positions) {
                setMarker(position)
            }
        }
    }

    private fun isMapReady() = googleMap != null

    private fun displayNewPoint(data: Intent) {

        if (main__bar__bottom_navigation.selectedItemId != R.id.main__menu_item__reviewed) {
            main__bar__bottom_navigation.selectedItemId = R.id.main__menu_item__reviewed
            loadReviewed()
        }

        if (data.hasExtra(NewPeePointActivity.EXTRA_RESULT_LAT) &&
                data.hasExtra(NewPeePointActivity.EXTRA_RESULT_LNG)) {
            val latitude = data.getDoubleExtra(NewPeePointActivity.EXTRA_RESULT_LAT, -1.0)
            val longitude = data.getDoubleExtra(NewPeePointActivity.EXTRA_RESULT_LAT, -1.0)
            googleMap?.addMarker(MarkerOptions().position(LatLng(latitude, longitude)))
        }
    }

    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        }
    }

    private fun showRationaleDialog() {
        showPermissionRationale(this, callback = object: RationaleCallback {
            override fun onChangeAnswer() {
                requestLocationPermission()
            }

            override fun onDontChangeAnswer() {
                onAddButtonClicked()
            }
        })
    }

    private fun hasLocationPermission() = hasPermission(MainActivity@this,
            Manifest.permission.ACCESS_FINE_LOCATION)
}
