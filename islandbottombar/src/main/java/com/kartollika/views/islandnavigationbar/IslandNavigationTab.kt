package com.kartollika.views.islandnavigationbar

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable

interface IslandNavigationTab {
    fun setTabTitle(title: String)
    fun getTabTitle(): String
    fun setTabIcon(drawable: Drawable?)
    fun setTabToggleDuration(duration: Int)
    fun setTabBackground(background: Drawable?)
    fun setTabTitleActiveColor(color: Int)
    fun setTabTitleInactiveColor(color: Int)
    fun setTabTitleColorsStateList(colorStateList: ColorStateList)
    fun setCustomInternalPadding(padding: Int)
}