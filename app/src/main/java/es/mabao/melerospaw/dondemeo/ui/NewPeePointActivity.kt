package es.mabao.melerospaw.dondemeo.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import es.mabao.melerospaw.dondemeo.R
import es.mabao.melerospaw.dondemeo.base.BaseActivity
import es.mabao.melerospaw.dondemeo.extensions.toast
import kotlinx.android.synthetic.main.activity_new_pee_point.*


class NewPeePointActivity : BaseActivity() {

    companion object {
        val REQUEST_CODE: Int = 0
        val REQUEST_CODE_PICTURE: Int = 1
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

    fun goBackWithResult() {
        val data = Intent()
        collectResultData(data)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    fun collectResultData(data: Intent) {
        data.putExtra(EXTRA_SOAP, new_pee_point__selector__soap.getSelectedOption())
    }

    private fun goBackWithoutResult() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
