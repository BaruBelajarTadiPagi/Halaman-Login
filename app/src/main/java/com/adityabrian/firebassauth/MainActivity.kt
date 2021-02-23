package com.adityabrian.firebassauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
        // membuat variabel dari firebass auth
    private lateinit var auth : FirebaseAuth
    private lateinit var mGoogleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebaseAuth()
        getData()

        /** Fun dari tombol saat nanti di klik auth. langsung mengeluarkan akun tersebut juga*/
        btnLogOut.setOnClickListener{
            auth.signOut()
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener{
                        Toast.makeText(this,"Success LogOut",Toast.LENGTH_SHORT).show()
                }
                /** Lalu saat di klik button logout maka akan muncul ke tampilan dari AuthActivity*/
            startActivity(Intent(this,AuthActivity::class.java))
            finish()
        }
    }

    private fun getData() {
            /**  JADI currentUser = mengecek saat kita login user tersebut ada atau tidak*/
        val user = auth.currentUser
        if(user != null){
                /** jadi ini untuk menampilkan apa bila benar user tersebut dari
                 *  auth (asal dari Firebase Auth)*/
            tvEmail.text = user.email
        }
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
}