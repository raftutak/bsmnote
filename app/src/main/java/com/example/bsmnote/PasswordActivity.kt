package com.example.bsmnote

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_password.*

class PasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.password_activity_title)
        setContentView(R.layout.activity_password)

        val sharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)
        button_savepassword.setOnClickListener {
            changePassword(sharedPreferences)
        }
    }

    private fun changePassword(sharedPreferences: SharedPreferences) {
        val newPassword: String = input_changepassword.text.toString()
        var savedPassword = sharedPreferences.getString("password", "")
        val notepadActivityIntent = Intent(this, NotepadActivity::class.java)


        when {
            newPassword.isEmpty() -> Toast.makeText(
                this,
                R.string.no_password_message,
                Toast.LENGTH_LONG
            ).show()
            newPassword == savedPassword -> Toast.makeText(
                this,
                getString(R.string.different_password_message),
                Toast.LENGTH_LONG
            ).show()
            else -> {
                sharedPreferences.edit().putString("password", newPassword).apply()
                Toast.makeText(this, getString(R.string.password_changed_message), Toast.LENGTH_LONG).show()
                startActivity(notepadActivityIntent)
            }
        }

        savedPassword = sharedPreferences.getString("password", "")
        Log.d("log", "savedPassword is: $savedPassword")
    }
}