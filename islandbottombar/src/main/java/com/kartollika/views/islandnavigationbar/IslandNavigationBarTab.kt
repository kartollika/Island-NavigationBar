package com.kartollika.views.islandnavigationbar

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


class IslandNavigationBarTab(
    context: Context,
    private val attrs: AttributeSet?,
    defStyleAttr: Int
) :
    LinearLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        initViews()
        initContent()
    }

    var tabId = 0
        set(value) {
            field = value
            id = value
        }

    var tabPosition: Int = 0
    var isTabSelected = false
        private set

    var tabTitle: String? = null
        set(value) {
            value?.let {
                if (value.isEmpty()) {
                    tabTitleTextView.visibility = View.GONE
                }
                tabTitleTextView.text = value
            }
            field = value
        }

    var tabIcon: Drawable? = null
        set(value) {
            value?.let {
                tabIconView.setImageDrawable(value)
            }
            field = value
        }

    var tabBackground: Drawable? = null
        set(value) {
            background = value
            field = value
        }

    var tabTitleActiveColor: Int = DEFAULT_ACTIONS_ACTIVE_COLOR
        set(value) {
            field = value
            if (isTabSelected) {
                tabTitleTextView.setTextColor(value)
            }
        }

    var tabTitleInactiveColor: Int = DEFAULT_ACTIONS_INACTIVE_COLOR
        set(value) {
            field = value
            if (!isTabSelected) {
                tabTitleTextView.setTextColor(value)
            }
        }

    var tabToggleDuration: Int = 0

    private lateinit var tabContainer: ViewGroup
    private lateinit var tabTitleTextView: TextView
    private lateinit var tabIconView: ImageView

    fun setInitialSelectedStatus(initialSelected: Boolean) {
        if (initialSelected) {
            isTabSelected = true

            tabIconView.isSelected = true
            if (background is TransitionDrawable) {
                val transitionDrawable = background as TransitionDrawable
                transitionDrawable.startTransition(tabToggleDuration)
            }
            tabTitleTextView.setTextColor(tabTitleActiveColor)
            tabTitleTextView.visibility = View.VISIBLE
        } else {
            isTabSelected = false
            tabIconView.isSelected = false
            tabTitleTextView.visibility = View.GONE
        }
    }

    fun selectTab() {
        tabIconView.isSelected = true
        if (background is TransitionDrawable) {
            val transitionDrawable = background as TransitionDrawable
            transitionDrawable.startTransition(tabToggleDuration)
        } else {
            background = tabBackground
        }
        tabTitleTextView.setTextColor(tabTitleActiveColor)
        tabTitleTextView.visibility = View.VISIBLE
    }

    fun deselectTab() {
        tabIconView.isSelected = false

        if (background is TransitionDrawable) {
            val transitionDrawable = background as TransitionDrawable
            transitionDrawable.reverseTransition(tabToggleDuration)
        } else {
            background = null
        }
        tabTitleTextView.setTextColor(tabTitleInactiveColor)
        tabTitleTextView.visibility = View.GONE
    }

    private fun initViews() {
        val tabLayoutContainer =
            LayoutInflater.from(context).inflate(R.layout.bottombar_item, this)
        with(tabLayoutContainer) {
            tabContainer = this@IslandNavigationBarTab
            tabTitleTextView = findViewById(R.id.bottombar_tab_textview)
            tabTitleTextView.setTextColor(tabTitleInactiveColor)
            tabIconView = findViewById(R.id.bottombar_tab_icon_imageview)
        }
        setInitialPadding()
    }

    private fun setInitialPadding() {
        setPadding(24, 16, 24, 16)
    }

    private fun initContent() {
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.IslandNavigationBarTab)
        tabId = id
        tabTitle = attributes.getString(R.styleable.IslandNavigationBarTab_tabTitle)
        tabIcon = attributes.getDrawable(R.styleable.IslandNavigationBarTab_tabIcon)
        tabBackground =
            attributes.getDrawable(R.styleable.IslandNavigationBarTab_tabBackground)
        tabToggleDuration = attributes.getInt(
            R.styleable.IslandNavigationBarTab_tabToggleDuration,
            DEFAULT_TOGGLE_BAR_ANIMATION_DURATION
        )

        if (attributes.hasValue(R.styleable.IslandNavigationBarTab_tabTitleActiveColor)) {
            tabTitleActiveColor = attributes.getColor(
                R.styleable.IslandNavigationBarTab_tabTitleActiveColor,
                DEFAULT_ACTIONS_ACTIVE_COLOR
            )
        }

        if (attributes.hasValue(R.styleable.IslandNavigationBarTab_tabTitleInactiveColor)) {
            tabTitleInactiveColor = attributes.getColor(
                R.styleable.IslandNavigationBarTab_tabTitleInactiveColor,
                DEFAULT_ACTIONS_INACTIVE_COLOR
            )
        }

        attributes.recycle()
    }

    private fun changeDrawableTint(drawable: Drawable?, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable?.setTint(color)
        } else {
            drawable?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    companion object {
        private const val DEFAULT_ACTIVE_COLOR_TAB_BACKGROUND = Color.LTGRAY
        private const val DEFAULT_INACTIVE_COLOR_TAB_BACKGROUND = Color.WHITE
        private const val DEFAULT_TOGGLE_BAR_ANIMATION_DURATION = 250
        private const val DEFAULT_ACTIONS_ACTIVE_COLOR = Color.GRAY
        private const val DEFAULT_ACTIONS_INACTIVE_COLOR = Color.GRAY
    }
}