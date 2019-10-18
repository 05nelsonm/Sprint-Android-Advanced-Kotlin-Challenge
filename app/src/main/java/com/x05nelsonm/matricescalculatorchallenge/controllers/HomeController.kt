package com.x05nelsonm.matricescalculatorchallenge.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.x05nelsonm.matricescalculatorchallenge.R
import com.x05nelsonm.matricescalculatorchallenge.showToast
import kotlinx.android.synthetic.main.layout_home_controller.view.*

class HomeController : Controller() {

    private lateinit var multiplyButton: Button
    private lateinit var context: HomeController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.layout_home_controller, container, false)

        multiplyButton = view.findViewById(R.id.button_multiply)
        context = this

        if (router.backstackSize > 1) {
            multiplyButton.visibility = View.GONE
        }

        return view
    }

    override fun onChangeEnded(
        changeHandler: ControllerChangeHandler,
        changeType: ControllerChangeType
    ) {
        super.onChangeEnded(changeHandler, changeType)

        multiplyButton.setOnClickListener {

            // Matrix A
            val etA11 = view?.edit_text_a11?.text.toString()
            val etA12 = view?.edit_text_a12?.text.toString()
            val etA21 = view?.edit_text_a21?.text.toString()
            val etA22 = view?.edit_text_a22?.text.toString()

            // Matrix B
            val etB11 = view?.edit_text_b11?.text.toString()
            val etB12 = view?.edit_text_b12?.text.toString()
            val etB21 = view?.edit_text_b21?.text.toString()
            val etB22 = view?.edit_text_b22?.text.toString()

            if (checkNotEmpty(etA11, etA12, etA21, etA22) && checkNotEmpty(etB11, etB12, etB21, etB22)) {

                // val matrixA = setValuesToMatrix(etA11, etA12, etA21, etA22)
                // val matrixB = setValuesToMatrix(etB11, etB12, etB21, etB22)

                router.pushController(
                    RouterTransaction.with(HomeController())
                        .pushChangeHandler(HorizontalChangeHandler())
                        .popChangeHandler(HorizontalChangeHandler())
                )
            } else {
                applicationContext?.showToast("All fields are required")
            }
        }
    }

    private fun checkNotEmpty(x11: String, x12: String, x21: String, x22: String): Boolean {
        val tempArray = setArray(x11, x12, x21, x22)

        var count = 0

        tempArray.forEach {
            if (it.isNotEmpty()) {
                count++
            }
        }

        return (count == 4)
    }

    private fun setArray(x11: String, x12: String, x21: String, x22: String): Array<String> {
        val tempArray = Array(4) { "" }
        tempArray[0] = x11
        tempArray[1] = x12
        tempArray[2] = x21
        tempArray[3] = x22
        return tempArray
    }
}