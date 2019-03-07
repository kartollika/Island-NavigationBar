package com.sample.kartollika.islandnavigationbar.activities

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.kartollika.views.islandnavigationbar.IslandNavigationBarView
import com.sample.kartollika.islandnavigationbar.R
import com.sample.kartollika.islandnavigationbar.fragments.ContentFragment

class MenuIslandBarActivity : AppCompatActivity() {

    private lateinit var colorsArray: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.islandbar_menu_sample)

        colorsArray = listOf(
            ContextCompat.getColor(this, R.color.color_home_fragment),
            ContextCompat.getColor(this, R.color.color_alarm_fragment),
            ContextCompat.getColor(this, R.color.color_favorite_fragment),
            ContextCompat.getColor(this, R.color.color_person_fragment)
        )

        initIslandBar()
    }

    private fun initIslandBar() {
        val islandBar = findViewById<IslandNavigationBarView>(R.id.navigation_bar)

        with(islandBar) {
            getTabById(R.id.tab_home).let {
                it.setTabTitleActiveColor(
                    ContextCompat.getColor(
                        this@MenuIslandBarActivity,
                        R.color.color_tab_home_actions_menu_1
                    )
                )
                it.setTabBackground(
                    ContextCompat.getDrawable(
                        this@MenuIslandBarActivity,
                        R.drawable.tab_home_background_menu_1
                    )
                )
                it.setTabToggleDuration(300)
            }
            getTabById(R.id.tab_alarm).let {
                it.setTabTitleActiveColor(
                    ContextCompat.getColor(
                        this@MenuIslandBarActivity,
                        R.color.color_tab_alarm_actions_menu_1
                    )
                )
                it.setTabBackground(
                    ContextCompat.getDrawable(
                        this@MenuIslandBarActivity,
                        R.drawable.tab_alarm_background_menu_1
                    )
                )
                it.setTabToggleDuration(300)
            }
            getTabById(R.id.tab_favorite).let {
                it.setTabTitleActiveColor(
                    ContextCompat.getColor(
                        this@MenuIslandBarActivity,
                        R.color.color_tab_favorite_actions_menu_1
                    )
                )
                it.setTabBackground(
                    ContextCompat.getDrawable(
                        this@MenuIslandBarActivity,
                        R.drawable.tab_favorite_background_menu_1
                    )
                )
                it.setTabToggleDuration(300)
            }
            getTabById(R.id.tab_person).let {
                it.setTabTitleActiveColor(
                    ContextCompat.getColor(
                        this@MenuIslandBarActivity,
                        R.color.color_tab_person_actions_menu_1
                    )
                )
                it.setTabBackground(
                    ContextCompat.getDrawable(
                        this@MenuIslandBarActivity,
                        R.drawable.tab_person_background_menu_1
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
                            islandBar.getTabById(tabId).getTabTitle(),
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