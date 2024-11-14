package com.ztt.expense.common

import android.content.Context
import android.widget.Toast

class ToastHelper {
    companion object {
        fun showToast(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}