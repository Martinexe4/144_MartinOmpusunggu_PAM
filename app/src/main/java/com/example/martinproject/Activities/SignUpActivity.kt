package com.example.martinproject.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.martinproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setUpActionBar()
    }

    private fun setUpActionBar(){
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_sign_up_activity)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_row)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }

        val btnSignUpIntro = findViewById<Button>(R.id.btn_sign_up_intro)

        btnSignUpIntro.setOnClickListener {
            registerUser()
        }


    }

    private fun registerUser(){
        val name: String = findViewById<EditText>(R.id.et_name).text.toString().trim { it <= ' ' }
        val email: String = findViewById<EditText>(R.id.et_email).text.toString().trim { it <= ' ' }
        val password: String = findViewById<EditText>(R.id.et_password).text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password)) {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                        {
                            task ->
                            hideProgressDialog()
                            if (task.isSuccessful){
                                val firebaseUser : FirebaseUser = task.result!!.user!!
                                val registeredEmail = firebaseUser.email!!

                                Toast.makeText(
                                    this@SignUpActivity,
                                    "Now we can register a new user.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                FirebaseAuth.getInstance().signOut()

                                finish()
                            } else{
                                Toast.makeText(
                                    this@SignUpActivity,
                                    "Sign in failed: " + task.exception?.message,                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter name.")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email.")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter password.")
                false
            }
            else -> {
                true
            }
        }
    }


}