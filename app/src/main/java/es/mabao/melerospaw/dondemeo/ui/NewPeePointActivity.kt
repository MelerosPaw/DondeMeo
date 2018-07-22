package es.mabao.melerospaw.dondemeo.ui

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import es.mabao.melerospaw.dondemeo.R
import es.mabao.melerospaw.dondemeo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_new_pee_point.*


class NewPeePointActivity : BaseActivity() {

    companion object {
        val REQUEST_CODE: Int = 1
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

    override fun onBackPressed() {
        goBackWithoutResult()
    }

    private fun loadView() {
        new_pee_point__btn__done.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun goBackWithoutResult() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
