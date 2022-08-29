package com.example.weatherapp.unit

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes

class DiaLog {
    companion object {
    fun createDialog(@LayoutRes layout: Int, context: Context, grayvity: Int): Dialog {
        val dialog = Dialog(context)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            setContentView(layout)
        }.also { dialog ->
            val window: Window = dialog.window!!
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            )
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val windowAttribute = window.attributes
            windowAttribute.gravity = grayvity
            window.attributes = windowAttribute
            return dialog
        }
    }
}
}