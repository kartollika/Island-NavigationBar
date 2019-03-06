package com.sample.kartollika.islandnavigationbar.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.kartollika.views.islandnavigationbar.IslandNavigationBar
import com.sample.kartollika.islandnavigationbar.R
import com.sample.kartollika.islandnavigationbar.fragments.ContentFragment

class XmlIslandBarActivity : AppCompatActivity() {

    private lateinit var islandBar: IslandNavigationBar
    private val colorsArray = listOf(Color.GREEN, Color.RED, Color.MAGENTA, Color.LTGRAY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.islandbar_xml_sample)
        initIslandBar()
    }

    private fun initIslandBar() {
        islandBar = findViewById(R.id.navigation_bar)

        islandBar.onTabSelectedListener = object : IslandNavigationBar.OnTabSelectedListener {
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
                    this@XmlIslandBarActivity,
                    "${islandBar.getTabPositionById(tabId)} tab was reselected",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTabUnselected(tabId: Int) {
                Toast.makeText(
                    this@XmlIslandBarActivity,
                    "${islandBar.getTabPositionById(tabId)} tab was unselected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}