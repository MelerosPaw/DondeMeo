package es.mabao.melerospaw.dondemeo.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.gms.maps.model.LatLng
import es.mabao.melerospaw.dondemeo.R
import es.mabao.melerospaw.dondemeo.base.BaseActivity
import es.mabao.melerospaw.dondemeo.extensions.toast
import kotlinx.android.synthetic.main.activity_new_pee_point.*


class NewPeePointActivity : BaseActivity() {

    var position: LatLng? = null

    companion object {
        val REQUEST_CODE: Int = 0
        val REQUEST_CODE_PICTURE: Int = 1
        val EXTRA_LAT: String = "EXTRA_LAT"
        val EXTRA_LNG: String = "EXTRA_LNG"
        val EXTRA_HAS_POINT: String = "EXTRA_HAS_POINT"
        val EXTRA_RESULT_LAT: String = "EXTRA_RESULT_LAT"
        val EXTRA_RESULT_LNG: String = "EXTRA_RESULT_LNG"

        fun intentWithPosition(context: Context, point: LatLng) =
            Intent(context, NewPeePointActivity::class.java)
                .putExtra(EXTRA_LAT, point.latitude)
                .putExtra(EXTRA_LNG, point.longitude)
                .putExtra(EXTRA_HAS_POINT, true)


        fun intentWithoutPosition(context: Context) =
                Intent(context, NewPeePointActivity::class.java)
                .putExtra(EXTRA_HAS_POINT, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pee_point)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            goBackWithoutResult()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PICTURE) {
            toast("Tengo foto")
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        goBackWithoutResult()
    }

    private fun loadView() {
        unboxIntent()

        new_pee_point__btn__done.setOnClickListener {
            goBackWithResult()
        }
        new_pee_point__btn__add_image.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_PICTURE)
        }
    }

    private fun unboxIntent() {
        if (intent.getBooleanExtra(EXTRA_HAS_POINT, false)) {
            position = LatLng(intent.getDoubleExtra(EXTRA_LAT, -1.0),
                    intent.getDoubleExtra(EXTRA_LNG, -1.0))
        }
    }

    fun goBackWithResult() {
        val data = Intent()
        collectResultData(data)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    fun collectResultData(data: Intent) {
        data.putExtra(EXTRA_RESULT_LAT, position?.latitude)
        data.putExtra(EXTRA_RESULT_LNG, position?.longitude)
    }

    private fun goBackWithoutResult() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
