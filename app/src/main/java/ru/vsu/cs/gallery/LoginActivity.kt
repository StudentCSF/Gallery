package ru.vsu.cs.gallery

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsu.cs.gallery.api.UserApi
import ru.vsu.cs.gallery.config.AppConfig
import ru.vsu.cs.gallery.model.dto.request.LoginRequest
import ru.vsu.cs.gallery.model.dto.response.AuthInfo
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
        var error = false

//        val p: Pattern = Pattern.compile("[+]?[78]?[() 0-9-]+")
        val p: Pattern = Pattern.compile("[0-9]{10}")
        val m: Matcher = p.matcher(login)

        if (login.isBlank()) {
            error = true
            lTil.isErrorEnabled = true
            lTil.error = resources.getString(R.string.empty_field)
        } else if (!m.matches() || login.length != 10) {
            error = true
            lTil.isErrorEnabled = true
            lTil.error = resources.getString(R.string.incorrect_login)
        }

        if (password.isBlank()) {
            error = true
            pTil.isErrorEnabled = true
            pTil.error = resources.getString(R.string.empty_field)
        } else if (password.length > 255 || password.length < 6) {
            error = true
            pTil.isErrorEnabled = true
            pTil.error = resources.getString(R.string.incorrect_password)
        }
        return error
    }

    fun login(view: View) {
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )

        val api: UserApi = AppConfig.USER_API

        val lTil = findViewById<TextInputLayout>(R.id.login_til)
        lTil.isErrorEnabled = false
        val login: String = findViewById<EditText>(R.id.login_et).text.toString()


        val pTil = findViewById<TextInputLayout>(R.id.password_til)
        pTil.isErrorEnabled = false
        val password: String = findViewById<EditText>(R.id.password_et).text.toString()

        if (isNotValid(login = login, password = password, lTil = lTil, pTil = pTil)) {
            return
        }

        val call: Call<AuthInfo> = api.login(
            LoginRequest(
                phone = lTil.prefixText.toString() + login,
                password = password
            )
        )
        call.enqueue(
            object : Callback<AuthInfo> {
                @SuppressLint("CommitPrefEdits")
                override fun onResponse(call: Call<AuthInfo>, response: Response<AuthInfo>) {
                    if (response.isSuccessful) {
                        info = response.body()

                        Log.i("info", info.toString())

                        getSharedPreferences(
                            AppConfig.APP_PREFERENCES,
                            MODE_PRIVATE
                        ).edit()
                            .apply {
                                putString(
                                    AppConfig.TOKEN,
                                    info?.token?.let { buildToken(it) }
                                )
                                putString(
                                    AppConfig.USER,
                                    Gson().toJson(info?.userInfo).toString()
                                )
                                apply()
                            }

                        startActivity(
                            Intent(
                                applicationContext, ProfileFragment::class.java
                            ).apply {
                                putExtra("user", info?.userInfo)
                            }
                        )
                    } else {
                        Toast.makeText(
                            applicationContext,
                            R.string.user_not_found,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AuthInfo>, t: Throwable) {
                }
            }
        )
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun buildToken(token: String): String {
        return getString(R.string.token_prefix) + " " + token
    }
}