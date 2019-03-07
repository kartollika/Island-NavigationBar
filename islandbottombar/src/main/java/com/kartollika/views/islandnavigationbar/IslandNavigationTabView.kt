package com.kartollika.views.islandnavigationbar

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class IslandNavigationTabView(
    context: Context,
    private val attrs: AttributeSet?,
    defStyleAttr: Int
) : LinearLayout(context, attrs, defStyleAttr),
    IslandNavigationTab {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    var tabId = 0
        set(value) {
            field = value
            id = value
        }

    var tabPosition: Int = 0
        internal set

    var isTabSelected = false
        private set

    private var tabTitle: String = ""
    private var tabIcon: Drawable? = null
    private var tabToggleDuration: Int = 0
    private var tabBackground: Drawable? = null

    private var tabTitleActiveColor: Int = DEFAULT_ACTIONS_ACTIVE_COLOR
    private var tabTitleInactiveColor: Int = DEFAULT_ACTIONS_INACTIVE_COLOR

    /* View fields */
    private lateinit var tabContainer: ViewGroup
    private lateinit var tabTitleTextView: TextView
    private lateinit var tabIconView: ImageView

    init {
        initViews()
        initContent()
    }

    internal fun setInitialSelectedStatus(initialSelected: Boolean) {
        if (initialSelected) {
            isTabSelected = true
            tabIconView.isSelected = true
            if (background is TransitionDrawable) {
                val transitionDrawable = background as TransitionDrawable
                transitionDrawable.startTransition(tabToggleDuration)
            }
            tabTitleTextView.setTextColor(tabTitleActiveColor)

            if (!tabTitle.isEmpty()) {
                tabTitleTextView.visibility = View.VISIBLE
            }
        } else {
            if (background !is TransitionDrawable) {
                tabBackground = null
            }
            isTabSelected = false
            tabIconView.isSelected = false
            tabTitleTextView.visibility = View.GONE
        }
    }

    internal fun selectTab() {
        tabIconView.isSelected = true
        tabTitleTextView.isSelected = true

        if (background is TransitionDrawable) {
            val transitionDrawable = background as TransitionDrawable
            transitionDrawable.startTransition(tabToggleDuration)
        } else {
            background = tabBackground
        }
        tabTitleTextView.setTextColor(tabTitleActiveColor)

        if (!tabTitle.isEmpty()) {
            tabTitleTextView.visibility = View.VISIBLE
        }
    }

    internal fun deselectTab() {
        tabIconView.isSelected = false
        tabTitleTextView.isSelected = false

        if (background is TransitionDrawable) {
            val transitionDrawable = background as TransitionDrawable
            transitionDrawable.reverseTransition(tabToggleDuration)
        } else {
            tabBackground = null
        }
        tabTitleTextView.setTextColor(tabTitleInactiveColor)
        tabTitleTextView.visibility = View.GONE
    }


    /* ======================================
     * IslandNavigationTabBar interface
     * ====================================== */

    override fun setTabTitle(title: String) {
        tabTitle = title
        tabTitleTextView.text = title
    }

    override fun getTabTitle(): String = tabTitle

    override fun setTabIcon(drawable: Drawable?) {
        tabIcon = drawable
        tabIconView.setImageDrawable(drawable)
    }

    override fun setTabToggleDuration(duration: Int) {
        tabToggleDuration = duration
    }

    override fun setTabTitleActiveColor(color: Int) {
        tabTitleActiveColor = color
        if (!isTabSelected) {
            tabTitleTextView.setTextColor(color)
        }
    }

    override fun setTabTitleInactiveColor(color: Int) {
        tabTitleActiveColor = color
        if (!isTabSelected) {
            tabTitleTextView.setTextColor(color)
        }
    }

    override fun setTabTitleColorsStateList(colorStateList: ColorStateList) {
        tabTitleTextView.setTextColor(colorStateList)
    }

    override fun setTabBackground(background: Drawable?) {
        tabBackground = background
        this.background = background
    }

    override fun setCustomInternalPadding(padding: Int) {
        setPadding(padding, padding, padding, padding)
    }

    /* ======================================
     * Private functions
     * ====================================== */

    private fun initContent() {
        tabId = id

        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.IslandNavigationTabView, 0, 0)

        if (attributes.hasValue(R.styleable.IslandNavigationTabView_tabTitle)) {
            setTabTitle(attributes.getString(R.styleable.IslandNavigationTabView_tabTitle)!!)
        }

        if (attributes.hasValue(R.styleable.IslandNavigationTabView_tabIcon)) {
            setTabIcon(attributes.getDrawable(R.styleable.IslandNavigationTabView_tabIcon))
        }

        if (attributes.hasValue(R.styleable.IslandNavigationTabView_tabBackground)) {
            setTabBackground(attributes.getDrawable(R.styleable.IslandNavigationTabView_tabBackground))
        }

        setTabToggleDuration(
            attributes.getInt(
                R.styleable.IslandNavigationTabView_tabToggleDuration,
                resources.getInteger(R.integer.default_tab_toggle_animation_duration)
            )
        )

        val defaultInternalPadding = resources.getDimension(R.dimen.default_tab_internal_padding)
        setCustomInternalPadding(
            attributes.getDimension(
                R.styleable.IslandNavigationTabView_tabCustomPadding,
                defaultInternalPadding
            ).toInt()
        )

        if (attributes.hasValue(R.styleable.IslandNavigationTabView_tabTitleActiveColor)) {
            tabTitleActiveColor = attributes.getColor(
                R.styleable.IslandNavigationTabView_tabTitleActiveColor,
                DEFAULT_ACTIONS_ACTIVE_COLOR
            )
        }

        if (attributes.hasValue(R.styleable.IslandNavigationTabView_tabTitleInactiveColor)) {
            tabTitleInactiveColor = attributes.getColor(
                R.styleable.IslandNavigationTabView_tabTitleInactiveColor,
                DEFAULT_ACTIONS_INACTIVE_COLOR
            )
        }

        attributes.recycle()
    }

    private fun initViews() {
        val tabLayoutContainer =
            LayoutInflater.from(context).inflate(R.layout.bottombar_item, this)
        with(tabLayoutContainer) {
            tabContainer = this@IslandNavigationTabView
            tabTitleTextView = findViewById(R.id.bottombar_tab_textview)
            tabTitleTextView.setTextColor(tabTitleInactiveColor)
            tabIconView = findViewById(R.id.bottombar_tab_icon_imageview)
        }
    }

//    private fun changeDrawableTint(drawable: Drawable?, color: Int) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            drawable?.setTint(color)
//        } else {
//            drawable?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
//        }
//    }

    companion object {
        private const val DEFAULT_ACTIONS_ACTIVE_COLOR = Color.GRAY
        private const val DEFAULT_ACTIONS_INACTIVE_COLOR = Color.GRAY
    }
}