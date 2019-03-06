package com.sample.kartollika.islandnavigationbar.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sample.kartollika.islandnavigationbar.R

class ContentFragment : Fragment() {

    private lateinit var innerText: String
    private var color: Int = Color.TRANSPARENT
    private lateinit var content: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            innerText = it.getString("innerText") ?: "No passed value"
            color = it.getInt("color")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.content_fragment, container, false)

        content = view.findViewById<View>(R.id.content).apply {
            this.setBackgroundColor(color)
        }

        view.findViewById<TextView>(R.id.content_fragment_textview).apply {
            text = innerText
        }
        return view
    }

    companion object {

        @JvmStatic
        fun getInstance(innerText: String, color: Int): ContentFragment {
            val fragment =
                ContentFragment()
            Bundle().apply {
                putString("innerText", innerText)
                putInt("color", color)
                fragment.arguments = this
            }
            return fragment
        }
    }
}