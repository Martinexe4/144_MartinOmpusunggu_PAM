package com.example.martinproject.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.martinproject.R
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setUpActionBar()

    }

    private fun setUpActionBar(){
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_sign_in_activity)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_row)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
        val btnSignInIntro = findViewById<Button>(R.id.btn_sign_in_intro)

        btnSignInIntro.setOnClickListener {
            signInRegisteredUser()
        }


    }

    private fun signInRegisteredUser() {
        val email: String = findViewById<EditText>(R.id.et_email).text.toString().trim { it <= ' ' }
        val password: String = findViewById<EditText>(R.id.et_password).text.toString().trim { it <= ' ' }


        if (validateForm(email,password)) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Sign-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {

                        Toast.makeText(
                            this@SignInActivity,
                            "You have successfully signed in.",
                            Toast.LENGTH_LONG
                        ).show()

                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }

    private fun validateForm(email: String, password: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            showErrorSnackBar("Please enter email.")
            false
        } else if (TextUtils.isEmpty(password)) {
            showErrorSnackBar("Please enter password.")
            false
        } else {
            true
        }
    }

}