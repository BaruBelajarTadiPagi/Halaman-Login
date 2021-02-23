package com.adityabrian.firebassauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

    class LoginActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth
        private lateinit var mGoogleSignInClient: GoogleSignInClient

        /** Lalu kita menambahkan key karena kita akan menggunakan on activity result*/
        companion object {
            const val RC_SIGN_IN = 100
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /** kita akan membuat func || untuk arrow yang sudah disediakan oleh AS*/
        initActionBar()

        initFirebaseAuth()

        // button ke arah lobby
        btnSignIn.setOnClickListener{
            val email = etEmailSignIn.text.toString().trim()
            val pass = etPassSignIn.text.toString().trim()

            if(checkValidation(email,pass)){
                loginToServer(email, pass)
            }
                /** kita tak butuh lagi startActivity lagi disini karena sudah ada
                 * di fun pengecekan apabila berhasil*/
        }

        // button ke halaman forgot pass
        btnForgotPass.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // mengaltifkan button toolbar di atas
        tbSignIn.setNavigationOnClickListener {
            finish()
        }

        btnGoogleSignIn.setOnClickListener{
            val signIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signIntent, RC_SIGN_IN)
        }
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if(requestCode == RC_SIGN_IN){
                CostumDialog.showLoading(this)
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                        // yang pentng kita mendapatkan credentialnya untuk bisa masuk firebase atuh
                    val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
                    firebaseAuth(credential)
                }catch (e:ApiException){ // saat kita backspace (kembali paksa) dia akan mengerjakan ini
                    CostumDialog.hideLoading()
                    Toast.makeText(this,"Sign In Canceled",Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun loginToServer(email: String, pass: String) {
                /** karena kita disini akan meng validati keduanya sekaligus, (contoh doc ada di doc android studio */
            val credential = EmailAuthProvider.getCredential(email,pass)
            firebaseAuth(credential)
        }

        private fun firebaseAuth(credential: AuthCredential) {
            auth.signInWithCredential(credential)
                .addOnCompleteListener{ task ->
                    CostumDialog.hideLoading()
                        // jika berhasil lalu kita cek
                    if (task.isSuccessful){
                        startActivity(Intent(this,MainActivity::class.java))
                        finishAffinity()
                    }else{
                        Toast.makeText(this, "Sign In Failed",Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener{exception ->
                    CostumDialog.hideLoading()
                    Toast.makeText(this, exception.message,Toast.LENGTH_SHORT).show()
                }
        }


        private fun checkValidation(email: String, pass: String): Boolean {
            if(email.isEmpty()){
                etEmailSignIn.error = "Please field your email"
                etEmailSignIn.requestFocus()
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmailSignIn.error = "Please use Valid email"
            }else if(pass.isEmpty()){
                etPassSignIn.error = "Please field your password"
                etPassSignIn.requestFocus()
            }else{
                // jika semua itu benar maka returnnya true
                return true
            }
                // jadi kita menampilkan loadingnya saat validation nya salah
            CostumDialog.hideLoading()
            return false
        }

        private fun initFirebaseAuth() {
            auth = FirebaseAuth.getInstance()

            // Configurate Google Sign In
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

            // disini setalah membuat variabel global mgoggleClient
            mGoogleSignInClient = GoogleSignIn.getClient(this,gso)
        }

        private fun initActionBar() {
        setSupportActionBar(tbSignIn) // berasal dari nama id toolbarnya di (xml)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""  // untuk title nya kita hapus
    }
}