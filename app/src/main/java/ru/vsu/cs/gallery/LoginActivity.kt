package ru.vsu.cs.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsu.cs.gallery.config.AppConfig
import ru.vsu.cs.gallery.model.dto.request.LoginRequest
import ru.vsu.cs.gallery.model.dto.response.AuthInfo
import ru.vsu.cs.gallery.api.LoginApi
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private var info: AuthInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    private fun isNotValid(
        login: String,
        password: String,
        lTil: TextInputLayout,
        pTil: TextInputLayout
    ): Boolean {
        var error: Boolean = true

//        val p: Pattern = Pattern.compile("[+]?[78]?[() 0-9-]+")
        val p: Pattern = Pattern.compile("[+7][0-9]{10}")
        val m: Matcher = p.matcher(login)

        if (login.isBlank()) {
            error = false
            lTil.isErrorEnabled = true
            lTil.error = resources.getString(R.string.empty_field)
        } else if (!m.matches() || login.length != 10) {
            error = false
            lTil.isErrorEnabled = true
            lTil.error = resources.getString(R.string.incorrect_login)
        }

        if (password.isBlank()) {
            error = false
            pTil.isErrorEnabled = true
            pTil.error = resources.getString(R.string.empty_field)
        } else if (password.length > 255 || password.length < 6) {
            error = false
            pTil.isErrorEnabled = true
            pTil.error = resources.getString(R.string.incorrect_password)
        }
        return error
    }

    fun login(view: View) {
        val api: LoginApi = AppConfig.LOGIN_API

        val login: String = findViewById<EditText>(R.id.login_et).text.toString()
        val lTil: TextInputLayout = findViewById<TextInputLayout>(R.id.login_til)
        lTil.isErrorEnabled = false

        val password: String = findViewById<EditText>(R.id.password_et).text.toString()
        val pTil: TextInputLayout = findViewById<TextInputLayout>(R.id.password_til)
        pTil.isErrorEnabled = false

        if (isNotValid(login = login, password = password, lTil = lTil, pTil = pTil)) {
            return
        }

        val call: Call<AuthInfo> = api.login(
            LoginRequest(
                phone = lTil.prefixText.toString() + login,
                password = password)
        )

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