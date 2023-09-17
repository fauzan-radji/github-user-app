package com.fauzan.githubuser.utils

import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar

class Error(view: View, private val text: String, private val tag: String? = null) {

    companion object {
        private const val TAG = "ErrorUtils"
    }

    private var snackbar: Snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)

    fun show() {
        snackbar.show()
        Log.e(tag ?: TAG, text)
    }
}