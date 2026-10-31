
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
    private lateinit var message: TextView
    private lateinit var actionButton: Button
    private lateinit var switchButton: Button

    // Yaha afno login email rakhnu
    private val ownerEmail = "YOUR_EMAIL@gmail.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            showHomeScreen()
        } else {
            showLoginScreen()
        }
    }

    private fun baseLayout(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(24, 24, 24, 24)
            setBackgroundColor(Color.rgb(15, 23, 42))
        }
    }

    private fun makeText(
        value: String,
        size: Float,
        color: Int
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            gravity = Gravity.CENTER
            setPadding(8, 12, 8, 12)
        }
    }

    private fun makeButton(
        value: String,
        action: (() -> Unit)? = null
    ): Button {
        return Button(this).apply {
            text = value
            setOnClickListener { action?.invoke() }
        }
    }

    private fun showLoginScreen() {
        val layout = baseLayout()

        layout.addView(makeText("⚽ WORLD EFOOTBALL", 28f, Color.WHITE))
        layout.addView(makeText("TOURNAMENT", 23f, Color.GREEN))

        emailInput = EditText(this).apply {
            hint = "Email address"
            setTextColor(Color.WHITE)
            setHintTextColor(Color.LTGRAY)
        }

        passwordInput = EditText(this).apply {
            hint = "Password (minimum 6 characters)"
            inputType = 129
            setTextColor(Color.WHITE)
            setHintTextColor(Color.LTGRAY)
        }

        actionButton = makeButton("LOGIN")
        switchButton = makeButton("Create New Account")
        message = makeText("", 15f, Color.YELLOW)

        layout.addView(emailInput)
        layout.addView(passwordInput)
        layout.addView(actionButton)
        layout.addView(switchButton)
        layout.addView(message)

        setContentView(layout)

        actionButton.setOnClickListener {
            if (isSignup) signup() else login()
        }

        switchButton.setOnClickListener {
            isSignup = !isSignup
            actionButton.text = if (isSignup) "SIGN UP" else "LOGIN"
            switchButton.text = if (isSignup)
                "Already have an account? Login"
            else "Create New Account"
            message.text = ""
        }
    }

    private fun login() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            message.text = "Enter email and password"
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showHomeScreen()
                } else {
                    message.text =
                        it.exception?.localizedMessage ?: "Login failed"
                }
            }
    }

    private fun signup() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (email.isEmpty() || password.length < 6) {
            message.text = "Enter valid email and password"
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showHomeScreen()
                } else {
                    message.text =
                        it.exception?.localizedMessage ?: "Signup failed"
                }
            }
    }

    private fun showHomeScreen() {
        val layout = baseLayout()

        layout.addView(makeText("⚽ WORLD EFOOTBALL", 28f, Color.WHITE))
        layout.addView(makeText("TOURNAMENT", 23f, Color.GREEN))

        layout.addView(
            makeText(
                "Welcome: ${auth.currentUser?.email}",
                15f,
                Color.WHITE
            )
        )

        layout.addView(makeButton("🏆 TOURNAMENT") {
            showTournament()
        })

        layout.addView(makeButton("👤 PLAYER PROFILE") {
            showSection("PLAYER PROFILE", "Player information")
        })

        layout.addView(makeButton("📅 MATCH SCHEDULE") {
            showSection("MATCH SCHEDULE", "Upcoming matches")
        })

        layout.addView(makeButton("🥇 LEADERBOARD") {
            showSection("LEADERBOARD", "Player rankings")
        })

        layout.addView(makeButton("🎮 OPEN EFOOTBALL") {
            try {
                val intent = packageManager.getLaunchIntentForPackage(
                    "com.konami.pesam"
                )
                if (intent != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "Please install official eFootball game",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Game not found", Toast.LENGTH_LONG).show()
            }
        })

        if (auth.currentUser?.email.equals(ownerEmail, ignoreCase = true)) {
            layout.addView(makeButton("🛡 OWNER DASHBOARD") {
                showOwnerDashboard()
            })
        }

        layout.addView(makeButton("LOGOUT") {
            auth.signOut()
            isSignup = false
            showLoginScreen()
        })

        val scroll = ScrollView(this)
        scroll.addView(layout)
        setContentView(scroll)
    }

    private fun showTournament() {
        val layout = baseLayout()

        layout.addView(makeText("🏆 TOURNAMENT", 26f, Color.GREEN))
        layout.addView(makeText("Choose Tournament Type", 18f, Color.WHITE))

        layout.addView(makeButton("FREE TOURNAMENT") {
            showSection(
                "FREE TOURNAMENT",
                "Free tournament registration will be available here."
            )
        })

        layout.addView(makeButton("PAID TOURNAMENT") {
            showSection(
                "PAID TOURNAMENT",
                "Paid tournament and entry fee details will be available here."
            )
        })

        layout.addView(makeButton("BACK") {
            showHomeScreen()
        })

        setContentView(layout)
    }

    private fun showOwnerDashboard() {
        val layout = baseLayout()

        layout.addView(makeText("🛡 OWNER DASHBOARD", 25f, Color.GREEN))

        layout.addView(makeButton("CREATE TOURNAMENT") {
            showSection(
                "CREATE TOURNAMENT",
                "Tournament creation form will be added here."
            )
        })

        layout.addView(makeButton("MANAGE PLAYERS") {
            showSection("MANAGE PLAYERS", "Player management")
        })

        layout.addView(makeButton("MANAGE RESULTS") {
            showSection("MANAGE RESULTS", "Match result management")
        })

        layout.addView(makeButton("PAYMENT MANAGEMENT") {
            showSection("PAYMENTS", "Payment management")
        })

        layout.addView(makeButton("BACK") {
            showHomeScreen()
        })

        setContentView(layout)
    }

    private fun showSection(title: String, description: String) {
        val layout = baseLayout()

        layout.addView(makeText(title, 25f, Color.GREEN))
        layout.addView(makeText(description, 17f, Color.WHITE))

        layout.addView(makeButton("BACK") {
            showHomeScreen()
        })

        setContentView(layout)
    }
}
