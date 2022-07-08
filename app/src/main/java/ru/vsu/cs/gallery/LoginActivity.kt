package ru.vsu.cs.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import ru.vsu.cs.gallery.config.AppConfig
import ru.vsu.cs.gallery.model.dto.request.LoginRequest
import ru.vsu.cs.gallery.model.dto.response.AuthInfo
import ru.vsu.cs.gallery.service.LoginService

class LoginActivity : AppCompatActivity() {
    private var info: AuthInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun login(view: View) {
        val retrofit: Retrofit = AppConfig.RETROFIT
        val service: LoginService = retrofit.create(LoginService::class.java)

        val login: String = findViewById<EditText>(R.id.login_et).text.toString()
        val password: String = findViewById<EditText>(R.id.password_et).text.toString()

        val call: Call<AuthInfo> = service.login(LoginRequest(phone = login, password = password))

        call.enqueue(
            object : Callback<AuthInfo> {
                override fun onResponse(call: Call<AuthInfo>, response: Response<AuthInfo>) {
                    if (response.isSuccessful) {
                        info = response.body()
                        Log.i("info", info.toString())
                    } else {
                        Log.e("call_er", "${response.code()}")
                    }
                }

                override fun onFailure(call: Call<AuthInfo>, t: Throwable) {
                }
            }
        )
    }
}