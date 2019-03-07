package com.sample.kartollika.islandnavigationbar.activities

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.kartollika.views.islandnavigationbar.IslandNavigationBarView
import com.sample.kartollika.islandnavigationbar.R
import com.sample.kartollika.islandnavigationbar.fragments.ContentFragment

class XmlIslandBarBorderItemsActivity : AppCompatActivity() {

    private lateinit var islandBar: IslandNavigationBarView
    private lateinit var colorsArray: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.islandbar_xml_borders_items_sample)

        colorsArray = listOf(
            ContextCompat.getColor(this, R.color.color_home_fragment),
            ContextCompat.getColor(this, R.color.color_alarm_fragment),
            ContextCompat.getColor(this, R.color.color_favorite_fragment),
            ContextCompat.getColor(this, R.color.color_person_fragment)
        )

        initIslandBar()
    }

    private fun initIslandBar() {
        islandBar = findViewById(R.id.navigation_bar)

        islandBar.getTabById(R.id.tabAlarm)

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