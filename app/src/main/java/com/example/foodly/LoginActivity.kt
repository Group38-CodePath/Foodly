package com.example.foodly


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        val firstObject = ParseObject("FirstClass")
//        firstObject.put("message","Hey ! First message from android. Parse is now connected")
//        firstObject.saveInBackground {
//            if (it != null){
//                it.localizedMessage?.let { message -> Log.e("LoginActivity", message) }
//            }else{
//                Log.d("LoginActivity","Object saved.")
//            }
//        }

        if (ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }

        findViewById<Button>(R.id.btn_login).setOnClickListener() {
            val username = findViewById<EditText>(R.id.edt_username).text.toString()
            val password = findViewById<EditText>(R.id.edt_password).text.toString()
            loginUser(username, password)
        }

        findViewById<Button>(R.id.btn_signup).setOnClickListener() {
            val username = findViewById<EditText>(R.id.edt_username).text.toString()
            val password = findViewById<EditText>(R.id.edt_password).text.toString()
            signUpUser(username, password)
        }
    }

    private fun signUpUser(username: String, password: String) {
        // Create the ParseUser
        val user = ParseUser()

// Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                Toast.makeText(this, "User Signed up Successfully", Toast.LENGTH_SHORT).show()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Username taken!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(
            username, password, ({ user, e ->
                if (user != null) {
                    goToMainActivity()
                    Log.i(TAG, "Successfully logged in user")
                } else {
                    e.printStackTrace()
                    Toast.makeText(this, "Error Logging in", Toast.LENGTH_SHORT).show()
                }
            })
        )
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
// Implement sign out to do

    companion object {
        const val TAG = "LoginActivity"
    }
}