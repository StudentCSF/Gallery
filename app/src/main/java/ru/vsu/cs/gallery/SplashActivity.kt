package ru.vsu.cs.gallery

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread {
            Thread.sleep(DELAY)
            this.startActivity(
                Intent(this, LoginActivity::class.java)
            )
            this.finish()
        }.start()

    }

    companion object {
        private const val DELAY: Long = 500
    }
}