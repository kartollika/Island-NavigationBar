package com.sample.kartollika.islandnavigationbar.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.kartollika.views.islandnavigationbar.IslandNavigationBarView
import com.sample.kartollika.islandnavigationbar.R
import com.sample.kartollika.islandnavigationbar.fragments.ContentFragment

class MenuIslandBarActivity : AppCompatActivity() {

    private val colorsArray = listOf(Color.GREEN, Color.RED, Color.MAGENTA, Color.LTGRAY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.islandbar_menu_sample)
        initIslandBar()
    }

    private fun initIslandBar() {
        val islandBar = findViewById<IslandNavigationBarView>(R.id.navigation_bar)

        with(islandBar) {
            getTabById(R.id.tab_home).let {
                it.setTabTitleActiveColor(Color.parseColor("#8122B4"))
                it.setTabBackground(
                    ContextCompat.getDrawable(
                        this@MenuIslandBarActivity,
                        R.drawable.tab_home_background
                    )
                )
                it.setTabToggleDuration(300)
            }
            getTabById(R.id.tab_person).let {
                it.setTabTitleActiveColor(Color.parseColor("#C90000"))
                it.setTabBackground(
                    ContextCompat.getDrawable(
                        this@MenuIslandBarActivity,
                        R.drawable.tab_person_background
                    )
                )
                it.setTabToggleDuration(300)
            }
            getTabById(R.id.tab_alarm).let {
                it.setTabTitleActiveColor(Color.parseColor("#308B00"))
                it.setTabBackground(
                    ContextCompat.getDrawable(
                        this@MenuIslandBarActivity,
                        R.drawable.tab_alarm_background
                    )
                )
                it.setTabToggleDuration(300)
            }
            getTabById(R.id.tab_favorite).let {
                it.setTabTitleActiveColor(Color.parseColor("#0071D5"))
                it.setTabBackground(
                    ContextCompat.getDrawable(
                        this@MenuIslandBarActivity,
                        R.drawable.tab_favorite_background
                    )
                )
                it.setTabToggleDuration(300)
            }
        }

        islandBar.setOnTabActionListener(object : IslandNavigationBarView.OnTabActionListener {
            override fun onTabSelected(tabId: Int) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        ContentFragment.getInstance(
                            "Fragment ${islandBar.getTabPositionById(tabId)}",
                            colorsArray[islandBar.getTabPositionById(tabId) % colorsArray.size]
                        )
                    )
                    .commit()
            }

            override fun onTabReselected(tabId: Int) {
            }

            override fun onTabUnselected(tabId: Int) {
            }
        })
    }
}