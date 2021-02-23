package com.adityabrian.firebassauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            checkAuth()

                /** didalam intent kita melakukan setelah splash screen dilanjutkan ke
                 *  screeem AuthActivity*/
//            startActivity(Intent(this, AuthActivity::class.java))
//            finish()
        },1200)
    }

    private fun checkAuth() {
        if(FirebaseAuth.getInstance().currentUser != null){
             /** jadii saat kita ingin login maka akan masuk langsung ke MainActivity*/
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }
}