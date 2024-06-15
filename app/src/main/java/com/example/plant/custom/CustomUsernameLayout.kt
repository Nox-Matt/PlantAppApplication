package com.example.plant.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class CustomUsernameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatEditText(context, attrs) {

init {
    addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //Do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s.toString().length > 14){
                error = "Username has to be less than 14 characters long"
            } else if(s.toString().length < 8){
                error = "Username have to be more than 8 characters long"
            }
        }

        override fun afterTextChanged(s: Editable?) {
            //Do Nothing
        }
            })
        }
    }
