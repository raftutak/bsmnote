package com.example.bsmnote

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isSignedUp: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.main_activity_title)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)
        updateLoginState(sharedPreferences)
        button_login.setOnClickListener {
            login(sharedPreferences)
        }
    }

    private fun login(sharedPreferences: SharedPreferences) {
        val password: String = input_password.text.toString()
        var savedPassword = sharedPreferences.getString("password", "")
        val notepadActivityIntent = Intent(this, NotepadActivity::class.java)

        if (savedPassword.isNullOrBlank()) {
            sharedPreferences.edit().putString("password", password).apply()
            Toast.makeText(this, getString(R.string.password_created_message), Toast.LENGTH_LONG)
                .show()
            startActivity(notepadActivityIntent)
            input_password.text.clear()
        } else {
            when {
                password.isEmpty() -> Toast.makeText(
                    this,
                    R.string.no_password_message,
                    Toast.LENGTH_LONG
                ).show()
                password != savedPassword -> Toast.makeText(
                    this,
                    getString(R.string.incorrect_password),
                    Toast.LENGTH_LONG
                ).show()
                password == savedPassword -> {
                    Toast.makeText(
                        this,
                        getString(R.string.login_success_message),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    startActivity(notepadActivityIntent)
                    input_password.text.clear()
                }
            }
        }

        savedPassword = sharedPreferences.getString("password", "")
        d("rawin", "savedPassword is: $savedPassword")
    }

    private fun updateLoginState(sharedPreferences: SharedPreferences) {
        val passwordExists = sharedPreferences.getString("password", "").isNullOrBlank()

        if (!passwordExists) {
            button_login.text = getString(R.string.login)
            login_info.text = getString(R.string.provide_password_info)
        } else {
            button_login.text = getString(R.string.create_password)
            login_info.text = getString(R.string.create_password_info)
        }
    }
}
