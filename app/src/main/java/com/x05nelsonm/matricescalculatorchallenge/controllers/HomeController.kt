package com.x05nelsonm.matricescalculatorchallenge.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.x05nelsonm.matricescalculatorchallenge.MatrixStringValues
import com.x05nelsonm.matricescalculatorchallenge.R
import com.x05nelsonm.matricescalculatorchallenge.model.TwoByTwoMatrix
import com.x05nelsonm.matricescalculatorchallenge.showToast

class HomeController : Controller() {

    private lateinit var multiplyButton: Button
    private lateinit var layoutLower: LinearLayout

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

    // Matrix A
    private lateinit var tvA11: TextView
    private lateinit var tvA12: TextView
    private lateinit var tvA21: TextView
    private lateinit var tvA22: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.layout_home_controller, container, false)

        multiplyButton = view.findViewById(R.id.button_multiply)
        layoutLower = view.findViewById(R.id.layout_lower)

        // Matrix A EditText Views
        etA11 = view.findViewById(R.id.edit_text_a11)
        etA12 = view.findViewById(R.id.edit_text_a12)
        etA21 = view.findViewById(R.id.edit_text_a21)
        etA22 = view.findViewById(R.id.edit_text_a22)

        // Matrix B EditText Views
        etB11 = view.findViewById(R.id.edit_text_b11)
        etB12 = view.findViewById(R.id.edit_text_b12)
        etB21 = view.findViewById(R.id.edit_text_b21)
        etB22 = view.findViewById(R.id.edit_text_b22)

        // Matrix A TextViews
        tvA11 = view.findViewById(R.id.text_view_a11)
        tvA12 = view.findViewById(R.id.text_view_a12)
        tvA21 = view.findViewById(R.id.text_view_a21)
        tvA22 = view.findViewById(R.id.text_view_a22)

        if (router.backstackSize > 1) {
            showEditTextViews(false)
            multiplyButton.visibility = View.GONE
            layoutLower.visibility = View.GONE
            setTextViewValues(MatrixStringValues.newValuesMatrix)
        } else {
            showEditTextViews(true)
        }

        return view
    }

    private fun showEditTextViews(yn: Boolean) {

        if (yn) {
            // Matrix A EditText Views
            etA11.visibility = View.VISIBLE
            etA12.visibility = View.VISIBLE
            etA21.visibility = View.VISIBLE
            etA22.visibility = View.VISIBLE

            // Matrix B EditText Views
            etB11.visibility = View.VISIBLE
            etB12.visibility = View.VISIBLE
            etB21.visibility = View.VISIBLE
            etB22.visibility = View.VISIBLE

            // Matrix A TextViews
            tvA11.visibility = View.GONE
            tvA12.visibility = View.GONE
            tvA21.visibility = View.GONE
            tvA22.visibility = View.GONE
        } else {
            // Matrix A EditText Views
            etA11.visibility = View.GONE
            etA12.visibility = View.GONE
            etA21.visibility = View.GONE
            etA22.visibility = View.GONE

            // Matrix B EditText Views
            etB11.visibility = View.GONE
            etB12.visibility = View.GONE
            etB21.visibility = View.GONE
            etB22.visibility = View.GONE

            // Matrix A TextViews
            tvA11.visibility = View.VISIBLE
            tvA12.visibility = View.VISIBLE
            tvA21.visibility = View.VISIBLE
            tvA22.visibility = View.VISIBLE
        }

    }

    override fun onChangeEnded(
        changeHandler: ControllerChangeHandler,
        changeType: ControllerChangeType
    ) {
        super.onChangeEnded(changeHandler, changeType)

        multiplyButton.setOnClickListener {

            // Matrix A
            val etA11String = etA11.text.toString()
            val etA12String = etA12.text.toString()
            val etA21String = etA21.text.toString()
            val etA22String = etA22.text.toString()

            // Matrix B
            val etB11String = etB11.text.toString()
            val etB12String = etB12.text.toString()
            val etB21String = etB21.text.toString()
            val etB22String = etB22.text.toString()

            if (
                checkNotEmpty(
                    etA11String,
                    etA12String,
                    etA21String,
                    etA22String
                ) &&
                checkNotEmpty(
                    etB11String,
                    etB12String,
                    etB21String,
                    etB22String
                )
            ) {
                val matrixA = TwoByTwoMatrix(
                    loadTwoByTwoMatrix(etA11String, etA12String, etA21String, etA22String)
                )
                val matrixB = TwoByTwoMatrix(
                    loadTwoByTwoMatrix(etB11String, etB12String, etB21String, etB22String)
                )

                // do multiplication
                val matrixC = matrixA * matrixB
                // turn back into strings

                MatrixStringValues.newValuesMatrix = setTextArray(
                    matrixC[0][0].toString(),
                    matrixC[0][1].toString(),
                    matrixC[1][0].toString(),
                    matrixC[1][1].toString()
                )

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
        val tempArray = setTextArray(x11, x12, x21, x22)

        var count = 0

        tempArray.forEach {
            if (it.isNotEmpty()) {
                count++
            }
        }

        return (count == 4)
    }

    private fun setTextArray(x11: String, x12: String, x21: String, x22: String): Array<String> {
        val tempArray = Array(4) { "" }
        tempArray[0] = x11
        tempArray[1] = x12
        tempArray[2] = x21
        tempArray[3] = x22
        return tempArray
    }

    private fun loadTwoByTwoMatrix(
        x11: String,
        x12: String,
        x21: String,
        x22: String
    ): Array<Array<Double>> {
        val twoByTwoMatrix = Array(2) { Array(2) { 0.0 } }
        val tempArray = setTextArray(x11, x12, x21, x22)

        tempArray.forEachIndexed { index, s ->
            val x = s.toDouble()
            when (index) {
                0 -> {
                    twoByTwoMatrix[0][0] = x
                }
                1 -> {
                    twoByTwoMatrix[0][1] = x
                }
                2 -> {
                    twoByTwoMatrix[1][0] = x
                }
                3 -> {
                    twoByTwoMatrix[1][1] = x
                }
            }
        }

        return twoByTwoMatrix
    }

    private fun setTextViewValues(arrayA: Array<String>) {

        // Matrix A
        tvA11.text = arrayA[0]
        tvA12.text = arrayA[1]
        tvA21.text = arrayA[2]
        tvA22.text = arrayA[3]
    }
}