package com.example.bsmnote

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_notepad.*

class NotepadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.notepad_activity_title)
        setContentView(R.layout.activity_notepad)

        val sharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)
        input_notecontent.setText(sharedPreferences.getString("note", ""))

        button_savenote.setOnClickListener {
            saveNote(sharedPreferences)
        }

        button_changepassword.setOnClickListener {
            gotoChangePassword()
        }

        button_logout.setOnClickListener {
            logout()
        }
    }

    private fun saveNote(sharedPreferences: SharedPreferences) {
        val newNote: String = input_notecontent.text.toString()
        var savedNote = sharedPreferences.getString("note", "")

        when {
            newNote.isEmpty() -> Toast.makeText(
                this,
                R.string.empty_note_message,
                Toast.LENGTH_LONG
            ).show()
            newNote == savedNote -> Toast.makeText(
                this,
                getString(R.string.different_note_message),
                Toast.LENGTH_LONG
            ).show()
            else -> {
                sharedPreferences.edit().putString("note", newNote).apply()
                Toast.makeText(
                    this,
                    getString(R.string.note_saved_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        savedNote = sharedPreferences.getString("note", "")
        Log.d("log", "savedNote is: $savedNote")
    }

    private fun gotoChangePassword() {
        val passwordActivityIntent = Intent(this, PasswordActivity::class.java)
        startActivity(passwordActivityIntent)
    }

    private fun logout() {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(mainActivityIntent)
    }
}