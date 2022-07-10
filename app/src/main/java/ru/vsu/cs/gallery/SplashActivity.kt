package ru.vsu.cs.gallery

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.vsu.cs.gallery.config.AppConfig

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread {
            Thread.sleep(DELAY)

            if (getSharedPreferences(
                    AppConfig.APP_PREFERENCES,
                    MODE_PRIVATE
                ).contains(AppConfig.TOKEN)
            ) {
                this.startActivity(
                    Intent(this, ProfileFragment::class.java)
                )
            } else {
                this.startActivity(
                    Intent(this, LoginActivity::class.java)
                )
            }
            this.finish()
        }.start()

    }

    companion object {
        private const val DELAY: Long = 500
    }
}