package com.kartollika.views.islandnavigationbar

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.IdRes
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.support.transition.TransitionSet
import android.support.v7.view.menu.MenuBuilder
import android.util.AttributeSet
import android.view.MenuInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator


class IslandNavigationBar(context: Context, private val attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    interface OnTabSelectedListener {
        fun onTabSelected(@IdRes tabId: Int)
        fun onTabReselected(@IdRes tabId: Int)
        fun onTabUnselected(@IdRes tabId: Int)
    }

    var onTabSelectedListener: OnTabSelectedListener? = null
    var chainMode: ChainMode = DEFAULT_BAR_DISTRIBUTION

    var tabToggleInterpolator: Interpolator = DEFAULT_TOGGLE_INTERPOLATOR
    var toggleDuration: Int = DEFAULT_TOGGLE_BAR_ANIMATION_DURATION

    enum class ChainMode(val constraintMode: Int) {
        SPREAD(ConstraintSet.CHAIN_SPREAD),
        PACKED(ConstraintSet.CHAIN_PACKED),
        SPREAD_INSIDE(ConstraintSet.CHAIN_SPREAD_INSIDE)
    }

    var currentTab: Int = 0
        set(value) {
            if (field == value || value < 0 || value + 1 > bottomBarTabs.size) {
                return
            }
            field = value
            bottomBarTabs[value].performClick()
        }

    var tabsCount: Int = 0
        private set

    private var bottomBarTabs: MutableList<IslandNavigationBarTab> = mutableListOf()

    init {
        initContent()

        post {
            reloadTabs()
        }
    }

    fun addTab(tab: IslandNavigationBarTab) {
        addView(tab)
    }

    fun addTabs(vararg tabs: IslandNavigationBarTab) {
        tabs.forEach { addTab(it) }
    }

    fun reloadTabs() {
        bottomBarTabs.clear()

        var index = 0
        for (i in 0 until childCount) {
            bottomBarTabs.add(getChildAt(i) as IslandNavigationBarTab)
            val tab = bottomBarTabs[i]
            tab.also {
                it.setOnClickListener { view ->
                    if (currentTab == it.tabPosition) {
                        onTabSelectedListener?.onTabReselected(bottomBarTabs[currentTab].tabId)
                    } else {
                        TransitionManager.beginDelayedTransition(
                            this@IslandNavigationBar,
                            TransitionSet()
                                .addTransition(ChangeBounds().apply {
                                    interpolator = tabToggleInterpolator
                                    duration = toggleDuration.toLong()
                                })
                        )
                        onTabSelectedListener?.onTabSelected(bottomBarTabs[it.tabPosition].let { tab ->
                            tab.selectTab()
                            tab.tabId
                        })
                        onTabSelectedListener?.onTabUnselected(bottomBarTabs[currentTab].let { tab ->
                            tab.deselectTab()
                            tab.tabId
                        })
                    }
                    currentTab = it.tabPosition
                }
                it.tabPosition = index++
                tab.setInitialSelectedStatus(currentTab == it.tabPosition)
            }
        }

        tabsCount = bottomBarTabs.size
        updateTabItems()
    }

    private fun updateTabItems() {
        createChains()
    }

    fun getTabPositionById(id: Int) = bottomBarTabs.find { it.tabId == id }?.tabPosition ?: -1

    fun getTabById(id: Int) = findViewById<IslandNavigationBarTab>(id)

    fun setChainStyleMode(chainMode: ChainMode) {
        this.chainMode = chainMode
        updateTabItems()
    }

    private fun createChains() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        val chainIdsList = IntArray(bottomBarTabs.size)
        val chainWeightList = FloatArray(bottomBarTabs.size)

        for (i in 0 until bottomBarTabs.size) {
            val id = bottomBarTabs[i].id
            chainIdsList[i] = id
            chainWeightList[i] = 0.0f
            constraintSet.connect(
                id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                0
            )
            constraintSet.connect(
                id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                0
            )
        }

        constraintSet.createHorizontalChain(
            id, ConstraintSet.LEFT,
            id, ConstraintSet.RIGHT,
            chainIdsList, chainWeightList,
            chainMode.constraintMode
        )
        constraintSet.applyTo(this)
    }

    /*private fun selectTab(newSelectedTab: Int) {
        for (tab in bottomBarTabs) {
            if (newSelectedTab == currentTab) {
                tab.selectTab()
            } else {
                tab.deselectTab()
            }
        }
    }*/

    private fun initContent() {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.IslandNavigationBar, 0, 0)

        if (attributes.hasValue(R.styleable.IslandNavigationBar_barMenu)) {
            val menuRes = attributes.getResourceId(R.styleable.IslandNavigationBar_barMenu, 0)
            initMenu(menuRes)
        }

        currentTab = attributes.getInt(R.styleable.IslandNavigationBar_barSelectedTab, 0)
        toggleDuration = attributes.getInt(
            R.styleable.IslandNavigationBarTab_tabToggleDuration,
            DEFAULT_TOGGLE_BAR_ANIMATION_DURATION
        )
        chainMode = ChainMode.values()[attributes.getInt(
            R.styleable.IslandNavigationBar_barDistribution,
            0
        )]

        if (attributes.hasValue(R.styleable.IslandNavigationBar_barToggleInterpolator)) {
            tabToggleInterpolator =
                AnimationUtils.loadInterpolator(
                    context,
                    attributes.getResourceId(
                        R.styleable.IslandNavigationBar_barToggleInterpolator,
                        DEFAULT_TOGGLE_BAR_ANIMATION_DURATION
                    )
                )
        }
        attributes.recycle()
    }

    @SuppressLint("RestrictedApi")
    private fun initMenu(menuRes: Int) {
        if (menuRes == 0) {
            return
        }

        val menu = MenuBuilder(context)
        MenuInflater(context).inflate(menuRes, menu)
        if (menu.size() > 5) {
            throw java.lang.IllegalArgumentException("Count of tabs of BottomBar must be less or equal to 5")
        }
        for (menuItem in menu.nonActionItems.also { it.addAll(menu.actionItems) }) {
            addTab(IslandNavigationBarTab(context).apply {
                id = menuItem.itemId
                tabId = menuItem.itemId
                tabTitle = menuItem.title.toString()
                tabIcon = menuItem.icon
            })
        }
    }

    companion object {
        private const val DEFAULT_TOGGLE_BAR_ANIMATION_DURATION = 250
        private val DEFAULT_BAR_DISTRIBUTION = ChainMode.SPREAD
        private val DEFAULT_TOGGLE_INTERPOLATOR = AccelerateDecelerateInterpolator()
    }
}