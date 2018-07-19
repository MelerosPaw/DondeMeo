package es.mabao.melerospaw.dondemeo.widgets

import android.text.Editable
import android.text.TextWatcher

interface SimpleTextWatcher: TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        // NO-OP Nothing to do here
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // NO-OP Nothing to do here
    }
}