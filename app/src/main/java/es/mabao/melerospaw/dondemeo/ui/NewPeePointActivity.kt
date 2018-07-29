package es.mabao.melerospaw.dondemeo.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.TextView
import es.mabao.melerospaw.dondemeo.R
import es.mabao.melerospaw.dondemeo.base.BaseActivity
import es.mabao.melerospaw.dondemeo.extensions.findFirstChild
import es.mabao.melerospaw.dondemeo.extensions.kitKatApiLevel
import kotlinx.android.synthetic.main.activity_new_pee_point.*


class NewPeePointActivity : BaseActivity() {

    companion object {
        val REQUEST_CODE: Int = 1
        val EXTRA_SOAP: String = "EXTRA_SOAP"
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
            val data = Intent()
            data.putExtra(EXTRA_SOAP, test.getSelectedOption())
            setResult(Activity.RESULT_OK, data)
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

        with(new_pee_point__selected_soap) {

            // Animates position
            val animationX = ObjectAnimator.ofFloat(this, "translationX", it.x)
            val animationY = ObjectAnimator.ofFloat(this, "translationY", it.y)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animationX, animationY)
            animatorSet.start()

            // Animates size
            kitKatApiLevel { TransitionManager.beginDelayedTransition(new_pee_point__container__soap_selector) }
            val currentLayoutParams = layoutParams
            currentLayoutParams.height = it.height
            currentLayoutParams.width = it.width
            layoutParams = currentLayoutParams
            if (visibility != View.VISIBLE) {
                visibility = VISIBLE
            }
        }
    }

    private fun getSelectedSoapOption() : String? {
        var selectedOption: String? = null

        if (new_pee_point__selected_soap.visibility != VISIBLE) {
            selectedOption = null
        } else {
            val selectedX = new_pee_point__selected_soap.x
            val selectedView: TextView? = new_pee_point__container__soap_options.findFirstChild { it.x == selectedX }
            if (selectedView != null) {
                selectedOption = selectedView.text.toString()
            }
        }
        return selectedOption
    }
}
