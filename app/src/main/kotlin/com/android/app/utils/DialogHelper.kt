package com.android.app.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.app.R

class DialogHelper(private val context: Context) {
    private lateinit var dialog: AlertDialog
    fun showMessageDialog(message: String, onClick: () -> Unit = {}) {
        hideDialog()
        dialog = AlertDialog
            .Builder(context)
            .setMessage(message)
            .setPositiveButton(R.string.btn_ok) { dialog, _ -> dialog.dismiss(); onClick() }
            .show()
    }

    @SuppressLint("InflateParams")
    fun showLoadingIndicator() {
        hideDialog()
        dialog = AlertDialog
            .Builder(context)
            .setCancelable(false)
            .setView(LayoutInflater.from(context).inflate(R.layout.dialog_progress, null))
            .show()
        dialog.window?.decorView?.apply {
            setBackgroundColor(Color.TRANSPARENT)
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }

    fun hideDialog() {
        if (::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
        }
    }
}