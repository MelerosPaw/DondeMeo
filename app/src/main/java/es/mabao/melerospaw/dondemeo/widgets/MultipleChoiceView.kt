package es.mabao.melerospaw.dondemeo.widgets

import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import es.mabao.melerospaw.dondemeo.R
import es.mabao.melerospaw.dondemeo.extensions.findFirstChild
import es.mabao.melerospaw.dondemeo.extensions.forEachChildView
import es.mabao.melerospaw.dondemeo.extensions.getChildList
import kotlinx.android.synthetic.main.multiple_choice_view.view.*

class MultipleChoiceView : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (isNotInitializedYet()) {
            initialize()
        }
    }

    private fun isNotInitializedYet() = multiple_choice_view__container__option_list == null && childCount != 0

    private fun initialize() {
        addViews()
        initializeHighlightView()
        setClickListeners()
    }

    private fun addViews() {
        val childList = getChildList()
        if (childList.any { it !is OptionView }) {
            throw IllegalStateException("${javaClass.simpleName} can only host ${OptionView::class.java.simpleName}s")
        } else {
            removeAllViews()
            View.inflate(context, R.layout.multiple_choice_view, this)
            for (view in childList) {
                multiple_choice_view__container__option_list.addView(view)
            }
        }
    }

    private fun initializeHighlightView() {
        // Sets selector view height to match_parent after adding all views
        val layoutParams = multiple_choice_view__view__highlight.layoutParams
        layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT
        multiple_choice_view__view__highlight.layoutParams = layoutParams
    }

    private fun setClickListeners() {
        multiple_choice_view__container__option_list.forEachChildView {
            it.setOnClickListener {
                moveHighlightToSelectedItem(it)
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun moveHighlightToSelectedItem(selectedView: View) {

        if (multiple_choice_view__view__highlight.visibility != View.VISIBLE) {
            moveWithoutAnimation(selectedView)
        } else {
            moveWithAnimation(selectedView)
        }
    }

    private fun moveWithoutAnimation(selectedView: View) {
        with(multiple_choice_view__view__highlight) {

            // Move
            x = selectedView.x
            y = selectedView.y

            // Resize
            resizeToMatchView(selectedView)

            // Show
            visibility = View.VISIBLE
            alpha = 0F
            ObjectAnimator.ofFloat(this, View.ALPHA, 1F).start()
        }
    }

    private fun moveWithAnimation(selectedView: View) {

        with(multiple_choice_view__view__highlight) {

            // Move
            val animationX = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, selectedView.x)
            val animationY = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, selectedView.y)
            val animatorSet = android.animation.AnimatorSet()
            animatorSet.playTogether(animationX, animationY)
            animatorSet.start()

            // Resize
            resizeToMatchView(selectedView)
        }
    }

    private fun resizeToMatchView(selectedView: View) {
        val currentLayoutParams = multiple_choice_view__view__highlight.layoutParams
        currentLayoutParams.height = selectedView.height
        currentLayoutParams.width = selectedView.width
        multiple_choice_view__view__highlight.layoutParams = currentLayoutParams
    }

    fun getSelectedOption() : String? {
        var selectedOption: String? = null

        if (multiple_choice_view__view__highlight.visibility != View.VISIBLE) {
            selectedOption = null
        } else {
            val selectedX = multiple_choice_view__view__highlight.x
            val selectedView: TextView? = multiple_choice_view__container__option_list.findFirstChild { it.x == selectedX }
            if (selectedView != null) {
                selectedOption = selectedView.text.toString()
            }
        }
        return selectedOption
    }
}