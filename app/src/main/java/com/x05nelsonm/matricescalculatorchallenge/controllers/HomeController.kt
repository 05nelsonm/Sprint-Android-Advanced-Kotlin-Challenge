package com.x05nelsonm.matricescalculatorchallenge.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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

    // Matrix A
    private lateinit var etA11: EditText
    private lateinit var etA12: EditText
    private lateinit var etA21: EditText
    private lateinit var etA22: EditText

    // Matrix B
    private lateinit var etB11: EditText
    private lateinit var etB12: EditText
    private lateinit var etB21: EditText
    private lateinit var etB22: EditText

    private lateinit var oldValuesMatrixA: Array<String>
    private lateinit var oldValuesMatrixB: Array<String>
    private lateinit var newValuesMatrixA: Array<String>
    private lateinit var newValuesMatrixB: Array<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.layout_home_controller, container, false)

        multiplyButton = view.findViewById(R.id.button_multiply)

        // Matrix A
        etA11 = view.findViewById(R.id.edit_text_a11)
        etA12 = view.findViewById(R.id.edit_text_a12)
        etA21 = view.findViewById(R.id.edit_text_a21)
        etA22 = view.findViewById(R.id.edit_text_a22)

        // Matrix B
        etB11 = view.findViewById(R.id.edit_text_b11)
        etB12 = view.findViewById(R.id.edit_text_b12)
        etB21 = view.findViewById(R.id.edit_text_b21)
        etB22 = view.findViewById(R.id.edit_text_b22)

        if (router.backstackSize > 1) {
            multiplyButton.visibility = View.GONE
            setEditTextValues(newValuesMatrixA, newValuesMatrixB, false)
        } else {
            if (view?.edit_text_a11?.text.toString().isNotEmpty()) {
                setEditTextValues(oldValuesMatrixA, oldValuesMatrixB, true)
            }
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
            val etA11String = etA11.toString()
            val etA12String = etA12.toString()
            val etA21String = etA21.toString()
            val etA22String = etA22.toString()

            // Matrix B
            val etB11String = etB11.toString()
            val etB12String = etB12.toString()
            val etB21String = etB21.toString()
            val etB22String = etB22.toString()

            if (checkNotEmpty(etA11String, etA12String, etA21String, etA22String) &&
                checkNotEmpty(etB11String, etB12String, etB21String, etB22String)) {

                oldValuesMatrixA = setArray(etA11String, etA12String, etA21String, etA22String)
                oldValuesMatrixB = setArray(etB11String, etB12String, etB21String, etB22String)

                //val matrixA = setValuesToMatrix(etA11String, etA12String, etA21String, etA22String)
                //val matrixB = setValuesToMatrix(etB11String, etB12String, etB21String, etB22String)

                // do multiplication
                // turn back into strings

                newValuesMatrixA = setArray(etA11String, etA12String, etA21String, etA22String)
                newValuesMatrixB = setArray(etB11String, etB12String, etB21String, etB22String)


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

    private fun setValuesToMatrix(x11: String, x12: String, x21: String, x22: String): Array<Array<Double>> {
        val twoByTwoMatrix = Array(2) { Array(2) {0.0} }
        val tempArray = setArray(x11, x12, x21, x22)

        tempArray.forEachIndexed { index, s ->
            val x = s.toDouble()
            when (index) {
                0 -> {twoByTwoMatrix[0][0] = x}
                1 -> {twoByTwoMatrix[0][1] = x}
                2 -> {twoByTwoMatrix[1][0] = x}
                3 -> {twoByTwoMatrix[1][1] = x}
            }
        }

        return twoByTwoMatrix
    }

    private fun setEditTextValues(arrayA: Array<String>, arrayB: Array<String>, enableEditText: Boolean) {
        // Matrix A
        etA11.setText(arrayA[0])
        etA11.isEnabled = enableEditText

        etA12.setText(arrayA[1])
        etA12.isEnabled = enableEditText

        etA21.setText(arrayA[2])
        etA21.isEnabled = enableEditText

        etA22.setText(arrayA[3])
        etA22.isEnabled = enableEditText

        // Matrix B
        etB11.setText(arrayB[0])
        etB11.isEnabled = enableEditText

        etB12.setText(arrayB[1])
        etB12.isEnabled = enableEditText

        etB21.setText(arrayB[2])
        etB21.isEnabled = enableEditText

        etB22.setText(arrayB[3])
        etB22.isEnabled = enableEditText
    }
}