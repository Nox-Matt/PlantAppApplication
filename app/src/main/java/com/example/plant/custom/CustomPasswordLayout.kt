package com.example.plant.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

class CustomPasswordLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        val editText = editText
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                error = if (password.length < 8) {
                    "Password must be at least 8 characters long"
                } else if (!password.matches(Regex(".*[0-9].*"))) {
                    "Password must contain at least one number"
                } else if (!password.matches(Regex(".*[!@#\$%^&*()_+=\\-`~{}|:\";'<>?,./].*"))) {
                    "Password must contain at least one symbol"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // No action
            }
        })
    }
}