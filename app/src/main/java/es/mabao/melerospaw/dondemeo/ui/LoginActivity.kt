package es.mabao.melerospaw.dondemeo.ui

import android.content.Intent
import android.os.Bundle
import es.mabao.melerospaw.dondemeo.R
import es.mabao.melerospaw.dondemeo.base.BaseActivity
import es.mabao.melerospaw.dondemeo.extensions.toast
import es.mabao.melerospaw.dondemeo.resourcesmanager.ResourceManager
import es.mabao.melerospaw.dondemeo.widgets.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private val simpleTextWatcher = object: SimpleTextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            checkEnableLoginButton()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpView()
        loadView()
    }

    private fun setUpView() {
        login__btn__sign_up.setBackgroundColor(ResourceManager.getColor(R.color.enabled_button_normal))
        login__btn__sign_up.setTextColor(ResourceManager.getColor(R.color.primaryText))
        login__btn__log_in.setOnClickListener { logIn() }
        login__btn__sign_up.setOnClickListener { toast("Abrir pantalla de registro") }
        login__btn__via_facebook.setOnClickListener { toast("Tú confía en mí, que esto llevará a la pantalla de incio de sesión con Facebook.") }
        login__btn__via_google.setOnClickListener { toast("Y se abre Google y tal y cual.") }
        login__input__user.addTextChangedListener(simpleTextWatcher)
        login__input__password.addTextChangedListener(simpleTextWatcher)
    }

    private fun loadView() {
//        checkEnableLoginButton()
    }

    private fun checkEnableLoginButton() {
        val enabled = login__input__user.text.isNotBlank() &&
                login__input__password.text.isNotBlank()
        val backgroundColorId = if (enabled) R.color.colorPrimary else R.color.enabled_button_normal
        val textColorId = if (enabled) R.color.primaryText else R.color.button_text_disabled

        login__btn__log_in.isEnabled = enabled
        login__btn__log_in.setBackgroundColor(ResourceManager.getColor(backgroundColorId))
        login__btn__log_in.setTextColor(ResourceManager.getColor(textColorId))
    }

    private fun logIn() {
        MainActivity.start(this)
//        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
