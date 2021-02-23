package com.adityabrian.firebassauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // kita akan mengaktifkan button Sign In
        btn_SignInAuth.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        // kita akan mengaktifkan button Sign Up
        btn_SignOutAuth.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }
}