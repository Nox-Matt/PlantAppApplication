package com.example.plant.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

class CustomUsernameLayout @JvmOverloads constructor(
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
                val username = s.toString()
                error = if (username.length < 8) {
                    "Username must be at least 8 characters long"
                } else if(username.length > 12) {
                    "Username must not be longer than 12 characters long"
                } else{
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // No action
            }
        })
    }
}