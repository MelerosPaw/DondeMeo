package es.mabao.melerospaw.dondemeo.ui

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import es.mabao.melerospaw.dondemeo.R
import es.mabao.melerospaw.dondemeo.base.BaseActivity
import es.mabao.melerospaw.dondemeo.extensions.toast
import es.mabao.melerospaw.dondemeo.ui.presenters.contracts.MainContract
import es.mabao.melerospaw.dondemeo.ui.presenters.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.View, OnMapReadyCallback {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private lateinit var presenter: MainContract.Presenter
    private var googleMap: GoogleMap? = null
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        loadView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == NewPeePointActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                displayNewPoint(data)
            } else {
                toast("Tranquilo que no ha llegado nada")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onUnexploredPointsRetrieved(positions: List<LatLng>) {
        viewModel.unexplored = positions
        presenter.removePoints(viewModel.reviewed)
        drawPoints(positions)
    }

    override fun onReviewedPointsRetrieved(positions: List<LatLng>) {
        viewModel.reviewed = positions
        presenter.removePoints(viewModel.unexplored)
        drawPoints(positions)
    }

    override fun showHistory() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showInfoWindowForMarker(marker: Marker) {
        marker.showInfoWindow()
    }

    override fun openPeePointDetail(position: LatLng) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
            startActivityForResult(Intent(this, NewPeePointActivity::class.java),
                    NewPeePointActivity.REQUEST_CODE)
        }
    }

    private fun setUpBottomBar() {
        main__bar__bottom_navigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.main__menu_item__show_unexplored -> { loadUnexplored() }
                R.id.main__menu_item__reviewed -> {
                    presenter.fetchReviewedPoints()
                    true
                }
                R.id.main__menu_item__history -> {
                    true
                }
                else -> false
            }
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

    private fun loadMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setUpMap(googleMap: GoogleMap) {
        setMarker(LatLng(-34.0, 151.0))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-34.0, 151.0)))
        googleMap.setOnMarkerClickListener { marker ->
            presenter.onMarkerClicked(marker)
            false
        }
    }

    private fun setMarker(position: LatLng) {
        googleMap?.addMarker(MarkerOptions().position(position))
    }

    private fun drawPoints(positions: List<LatLng>) {
        if (isMapReady()) {
            for (position: LatLng in positions) {
                setMarker(position)
            }
        }
    }

    private fun isMapReady() = googleMap != null

    private fun displayNewPoint(data: Intent?) {
        toast("Ha llegado un punto de orina nuevo con jabón: " + data?.getStringExtra(NewPeePointActivity.EXTRA_SOAP))
    }

}
