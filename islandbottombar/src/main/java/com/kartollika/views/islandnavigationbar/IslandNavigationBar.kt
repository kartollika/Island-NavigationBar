package com.kartollika.views.islandnavigationbar

import android.support.annotation.MenuRes
import android.view.animation.Interpolator

interface IslandNavigationBar {
    fun inflateBarFromMenu(@MenuRes menuRes: Int)
    fun setBarSelectedTab(position: Int)
    fun setBarToggleTabsInterpolator(interpolator: Interpolator)
    fun setBarTabsToggleDuration(duration: Int)
    fun setBarTabsDistribution(chainMode: IslandNavigationBarView.ChainMode)
    fun setOnTabActionListener(listener: IslandNavigationBarView.OnTabActionListener)
}