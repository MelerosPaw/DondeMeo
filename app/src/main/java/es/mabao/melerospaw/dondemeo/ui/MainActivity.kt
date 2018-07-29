package es.mabao.melerospaw.dondemeo.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import es.mabao.melerospaw.dondemeo.R
import es.mabao.melerospaw.dondemeo.base.BaseActivity
import es.mabao.melerospaw.dondemeo.extensions.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private fun loadView() {
        main__btn__add.setOnClickListener { startActivityForResult(Intent(this, NewPeePointActivity::class.java), NewPeePointActivity.REQUEST_CODE) }
    }

    private fun displayNewPoint(data: Intent?) {
        toast("Ha llegado un punto de orina nuevo con jabón: " + data?.getStringExtra(NewPeePointActivity.EXTRA_SOAP))
    }

}
