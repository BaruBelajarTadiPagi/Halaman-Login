package com.adityabrian.firebassauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_main.*

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        initActionBar()

        btnSendEmail.setOnClickListener {
            val email = etEmailForgotPass.text.toString().trim()
                // Lalu kita ingin memvalidasi
            if(email.isEmpty()){
                etEmailForgotPass.error = "Please field your Email"
                etEmailForgotPass.requestFocus()
                return@setOnClickListener
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ // kita cek lagi kecocokannya
                etEmailForgotPass.error = "Please use valid email"
                etEmailForgotPass.requestFocus()
                return@setOnClickListener
            }else{
                forgotPass(email)
            }
        }

        // arrow toolbar
        tbForgotPass.setNavigationOnClickListener {
            finish()
        }
    }

    private fun forgotPass(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Your Reset Password has been send to your email",Toast.LENGTH_LONG).show()
                            // apabila sdah berhasil kita pindah ke LoginActivity
                        startActivity(Intent(this,LoginActivity::class.java))
                        finishAffinity()
                    }else{
                        Toast.makeText(this, "Failed reset Password",Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, exception.message,Toast.LENGTH_LONG).show()
                }
    }


    private fun initActionBar() {
        setSupportActionBar(tbForgotPass)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}