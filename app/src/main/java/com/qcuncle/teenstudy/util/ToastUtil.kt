package com.qcuncle.teenstudy.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}