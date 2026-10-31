
package com.worldefootball.tournament

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : Activity() {

    private lateinit var auth: FirebaseAuth
    private var isSignup = false

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var actionButton: Button
    private lateinit var switchButton: Button
    private lateinit var message: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        showLoginScreen()
    }

    private fun showLoginScreen() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(30, 30, 30, 30)
        layout.setBackgroundColor(Color.rgb(15, 23, 42))

        val title = TextView(this)
        title.text = "⚽ WORLD EFOOTBALL"
        title.textSize = 27f
        title.setTextColor(Color.WHITE)
        title.gravity = Gravity.CENTER

        val subtitle = TextView(this)
        subtitle.text = "TOURNAMENT"
        subtitle.textSize = 22f
        subtitle.setTextColor(Color.GREEN)
        subtitle.gravity = Gravity.CENTER

        emailInput = EditText(this)
        emailInput.hint = "Email address"
        emailInput.setSingleLine(true)
        emailInput.setTextColor(Color.WHITE)
        emailInput.setHintTextColor(Color.LTGRAY)

        passwordInput = EditText(this)
        passwordInput.hint = "Password (minimum 6 characters)"
        passwordInput.setSingleLine(true)
        passwordInput.inputType = 129
        passwordInput.setTextColor(Color.WHITE)
        passwordInput.setHintTextColor(Color.LTGRAY)

        actionButton = Button(this)
        actionButton.text = "LOGIN"

        switchButton = Button(this)
        switchButton.text = "Create New Account"

        message = TextView(this)
        message.setTextColor(Color.YELLOW)
        message.gravity = Gravity.CENTER
        message.textSize = 15f

        layout.addView(title)
        layout.addView(subtitle)
        layout.addView(emailInput)
        layout.addView(passwordInput)
        layout.addView(actionButton)
        layout.addView(switchButton)
        layout.addView(message)

        setContentView(layout)

        actionButton.setOnClickListener {
            if (isSignup) {
                signup()
            } else {
                login()
            }
        }

        switchButton.setOnClickListener {
            isSignup = !isSignup

            if (isSignup) {
                actionButton.text = "SIGN UP"
                switchButton.text = "Already have an account? Login"
            } else {
                actionButton.text = "LOGIN"
                switchButton.text = "Create New Account"
            }

            message.text = ""
        }
    }

    private fun login() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            message.text = "Please enter email and password"
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    message.text = "Login successful!"
                    showHomeScreen()
                } else {
                    message.text = task.exception?.localizedMessage
                        ?: "Login failed"
                }
            }
    }

    private fun signup() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (email.isEmpty() || password.length < 6) {
            message.text = "Enter email and password (minimum 6 characters)"
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    message.text = "Account created successfully!"
                    showHomeScreen()
                } else {
                    message.text = task.exception?.localizedMessage
                        ?: "Signup failed"
                }
            }
    }

    private fun showHomeScreen() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(24, 24, 24, 24)
        layout.setBackgroundColor(Color.rgb(15, 23, 42))

        val title = TextView(this)
        title.text = "⚽ WORLD EFOOTBALL TOURNAMENT"
        title.textSize = 25f
        title.setTextColor(Color.WHITE)
        title.gravity = Gravity.CENTER

        val email = TextView(this)
        email.text = "Welcome: ${auth.currentUser?.email}"
        email.textSize = 16f
        email.setTextColor(Color.GREEN)
        email.gravity = Gravity.CENTER

        val logout = Button(this)
        logout.text = "LOGOUT"
        logout.setOnClickListener {
            auth.signOut()
            isSignup = false
            showLoginScreen()
        }

        layout.addView(title)
        layout.addView(email)
        layout.addView(logout)

        setContentView(layout)
    }
}
