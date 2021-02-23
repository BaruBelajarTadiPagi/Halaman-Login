package com.adityabrian.firebassauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initActionBar()

        // btn sign up
        btn_signUp.setOnClickListener {
            val email = etEmailSignUp.text.toString().trim()
            val pass = etPasswordSignUp.text.toString().trim()
            val confrimPass = etConfirmPassSignUp.text.toString().trim()

                // jadi saat di mulai akan muncul loading dan dia akan check
            CostumDialog.showLoading(this)

            /** selanjutnya kita buat fun check ke valid an*/
            if (checkValidation(email, pass, confrimPass)) {
                registerToServer(email, pass)
            }
        }
        tbSignUp.setNavigationOnClickListener {
            finish()
        }
    }

    private fun registerToServer(email: String, pass: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, pass)
                // MENULIS APA BILA BERHASIL
            .addOnCompleteListener { task ->
                CostumDialog.hideLoading() /** lalu kita menambahkannya di registrasi*/

                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                    // mendestroy seluruh stuck activity sebelumnya
                    /** sebelumnya ada Out Activity dan sebelumnya lagi
                     *  ada splash , lalu di destroy semua lalu masuk ke MainActivity*/
                }
            }
            // MENULIS APABILA GAGAL
            .addOnFailureListener {
                CostumDialog.hideLoading() /** lalu kita menambahkannya di registrasi saat gagal*/
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
    }

    // mengecek email, pass dan confirm pass
    private fun checkValidation(email: String, pass: String, confrimPass: String): Boolean {
        if (email.isEmpty()) {
            etEmailSignUp.error = "Please field your Email"
            etEmailSignUp.requestFocus()
            // jadi mengecek semisal tidak maching dengan input email biasnaya
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailSignUp.error = "Please use validation email"
            etEmailSignUp.requestFocus()
        } else if (pass.isEmpty()) {
            etPasswordSignUp.error = "Please field your Password"
            etPasswordSignUp.requestFocus()
        } else if (confrimPass.isEmpty()) {
            etConfirmPassSignUp.error = "Please field your Confirm Password"
            etConfirmPassSignUp.requestFocus()
        }else if (pass != confrimPass){
            etPasswordSignUp.error = "Your Pass didn't match"
            etConfirmPassSignUp.error = "Your Confirm didn't match"

            etPasswordSignUp.requestFocus()
            etConfirmPassSignUp.requestFocus()
        }else {
            etPasswordSignUp.error = null
            etConfirmPassSignUp.error = null
            return true
        }
            /** jadi kita akan menampilkan hideLoading() di Validation*/
        CostumDialog.hideLoading()
        return false
    }


    private fun initActionBar() {
        setSupportActionBar(tbSignUp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // kita beri true biar ada arrow backnya
        supportActionBar?.title = "" // jadi kita kosongkan text di samping arrownya
    }
}