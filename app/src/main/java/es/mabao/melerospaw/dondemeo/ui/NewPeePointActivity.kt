package es.mabao.melerospaw.dondemeo.ui

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.view.MenuItem
import android.view.View
import android.view.View.*
import es.mabao.melerospaw.dondemeo.R
import es.mabao.melerospaw.dondemeo.base.BaseActivity
import es.mabao.melerospaw.dondemeo.extensions.kitKatApiLevel
import es.mabao.melerospaw.dondemeo.extensions.toPixels
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

        new_pee_point__selected_soap.visibility = INVISIBLE
        new_pee_point__label__there_is_soap.setOnClickListener(soapOnClickListener)
        new_pee_point__label__there_isnt_soap.setOnClickListener(soapOnClickListener)
        new_pee_point__label__havent_checked_soap.setOnClickListener(soapOnClickListener)
    }

    private fun goBackWithoutResult() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private val soapOnClickListener = View.OnClickListener {
        kitKatApiLevel { TransitionManager.beginDelayedTransition(new_pee_point__container__soap_selector) }
        with(new_pee_point__selected_soap) {
            x = it.x
            y = it.y
            val currentLayoutParams = layoutParams
            currentLayoutParams.height = it.height
            currentLayoutParams.width = it.width
            layoutParams = currentLayoutParams
            if (visibility != View.VISIBLE) {
                visibility = VISIBLE
            }
        }
    }
}
