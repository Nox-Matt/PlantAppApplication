package com.example.plant.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class CustomPasswordLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (password.length < 8) {
                    error = "Password must be at least 8 characters"
                } else if (!password.contains(Regex("[0-9]"))) {
                    error = "Password must contain at least one number"
                } else if (!password.contains(Regex("[^A-Za-z0-9]"))) {
                    error = "Password must contain at least one special character"
                } else {
                    error = null
                }
            }
        })
    }
}