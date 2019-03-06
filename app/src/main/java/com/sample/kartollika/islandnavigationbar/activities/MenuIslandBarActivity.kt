package com.sample.kartollika.islandnavigationbar.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.kartollika.views.islandnavigationbar.IslandNavigationBar
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
        val islandBar = findViewById<IslandNavigationBar>(R.id.navigation_bar)

        with(islandBar) {
            getTabById(R.id.tab_home)?.let {
                it.tabTitleActiveColor = Color.parseColor("#8122B4")
                it.tabBackground = ContextCompat.getDrawable(
                    this@MenuIslandBarActivity,
                    R.drawable.tab_home_background
                )
                it.tabToggleDuration = 300
            }
            getTabById(R.id.tab_person)?.let {
                it.tabTitleActiveColor = Color.parseColor("#C90000")
                it.tabBackground = ContextCompat.getDrawable(
                    this@MenuIslandBarActivity,
                    R.drawable.tab_person_background
                )
                it.tabToggleDuration = 300
            }
            getTabById(R.id.tab_alarm)?.let {
                it.tabTitleActiveColor = Color.parseColor("#308B00")
                it.tabBackground = ContextCompat.getDrawable(
                    this@MenuIslandBarActivity,
                    R.drawable.tab_alarm_background
                )
                it.tabToggleDuration = 300
            }
            getTabById(R.id.tab_favorite)?.let {
                it.tabTitleActiveColor = Color.parseColor("#0071D5")
                it.tabBackground = ContextCompat.getDrawable(
                    this@MenuIslandBarActivity,
                    R.drawable.tab_favorite_background
                )
                it.tabToggleDuration = 300
            }
        }

        islandBar.onTabSelectedListener =
            object : IslandNavigationBar.OnTabSelectedListener {
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
                    Toast.makeText(
                        this@MenuIslandBarActivity,
                        "${islandBar.getTabPositionById(tabId)} tab was reselected",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onTabUnselected(tabId: Int) {
                    Toast.makeText(
                        this@MenuIslandBarActivity,
                        "${islandBar.getTabPositionById(tabId)} tab was unselected",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}