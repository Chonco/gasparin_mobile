package com.brunocarlos.inputmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener { onLoginButton() }
    }

    private fun onLoginButton() {
        val password = findViewById<EditText>(R.id.password_login_input).text.toString()

        if (password != "gasparin") {
            Toast.makeText(
                this,
                "Invalid Credentials!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        when (findViewById<EditText>(R.id.email_login_input).text.toString()) {
            "restaurant@gasparin.com" -> {
                val intent = Intent(this, RestaurantView::class.java)
                startActivity(intent)
            }
            "seller@gasparin.com" -> {
                val intent = Intent(this, SellerView::class.java)
                startActivity(intent)
            }
            else -> {
                Toast.makeText(
                    this,
                    "Invalid Credentials!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}