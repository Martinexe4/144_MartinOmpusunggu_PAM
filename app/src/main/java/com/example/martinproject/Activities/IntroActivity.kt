package com.example.martinproject.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

import android.widget.Button
import com.example.martinproject.R


class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val btnSignUpIntro = findViewById<Button>(R.id.btn_sign_up_intro)
        val btnSignInIntro = findViewById<Button>(R.id.btn_sign_in_intro)



        btnSignUpIntro.setOnClickListener {
            val bundle = Bundle()
            val intent= Intent(this@IntroActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        btnSignInIntro.setOnClickListener {
            val bundle = Bundle()
            val intent= Intent(this@IntroActivity, SignInActivity::class.java)
            startActivity(intent)
        }

    }
}