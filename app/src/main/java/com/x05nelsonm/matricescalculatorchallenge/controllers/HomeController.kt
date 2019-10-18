package com.x05nelsonm.matricescalculatorchallenge.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.x05nelsonm.matricescalculatorchallenge.R
import kotlinx.android.synthetic.main.layout_home_controller.view.*

class HomeController : Controller() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.layout_home_controller, container, false)

        val etA11 = view.edit_text_a11.text.toString()
        val etA12 = view.edit_text_a12.text.toString()
        val etA21 = view.edit_text_a21.text.toString()
        val etA22 = view.edit_text_a22.text.toString()
        val etB11 = view.edit_text_b11.text.toString()
        val etB12 = view.edit_text_b12.text.toString()
        val etB21 = view.edit_text_b21.text.toString()
        val etB22 = view.edit_text_b22.text.toString()

        return view
    }
}