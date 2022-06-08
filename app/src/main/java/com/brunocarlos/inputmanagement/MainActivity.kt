package com.brunocarlos.inputmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener { onLoginButton() }
    }

    private fun onLoginButton() {
        val password = findViewById<EditText>(R.id.password_login_input).text
        if (!password.equals("gasparin")) {
            Toast.makeText(
                this,
                "Invalid Credentials!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val email = findViewById<EditText>(R.id.email_login_input).text
        if (email.equals("restaurant@gasparin.com")) {

        } else if (email.equals("seller@gasparin.com")) {

        } else {
            Toast.makeText(
                this,
                "Invalid Credentials!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}