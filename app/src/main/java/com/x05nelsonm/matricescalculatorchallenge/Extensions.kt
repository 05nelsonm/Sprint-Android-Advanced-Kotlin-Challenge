package com.x05nelsonm.matricescalculatorchallenge

import android.content.Context
import android.widget.Toast

// Toast
fun Context.showToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}