package es.mabao.melerospaw.dondemeo.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import es.mabao.melerospaw.dondemeo.R

class OptionView : TextView {

    private var isInitialized = false

    constructor(context: Context) : super(context) {
        if (isInEditMode) {
            init()
        }
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        if (isInEditMode) {
            init()
        }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (isInEditMode) {
            init()
        }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        if (isInEditMode) {
            init()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (!isInitialized) {
            init()
            isInitialized = true
        }
    }

    private fun init() {
        val currentLayoutParams = layoutParams
        currentLayoutParams.width = 0
        currentLayoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
        (currentLayoutParams as LinearLayout.LayoutParams).weight = 1F
        layoutParams = currentLayoutParams

        val paddingTopBottom = resources.getDimensionPixelSize(R.dimen.new_pee_point__item_padding)
        setPadding(paddingLeft, paddingTopBottom, paddingRight, paddingTopBottom)
        gravity = Gravity.CENTER_HORIZONTAL
    }
}