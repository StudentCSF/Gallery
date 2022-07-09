package ru.vsu.cs.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsu.cs.gallery.config.AppConfig
import ru.vsu.cs.gallery.model.dto.request.LoginRequest
import ru.vsu.cs.gallery.model.dto.response.AuthInfo
import ru.vsu.cs.gallery.api.LoginApi
import java.lang.Exception
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
        var error: Boolean = false

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

        val api: LoginApi = AppConfig.LOGIN_API

        val lTil: TextInputLayout = findViewById<TextInputLayout>(R.id.login_til)
        lTil.isErrorEnabled = false
        val login: String = findViewById<EditText>(R.id.login_et).text.toString()


        val pTil: TextInputLayout = findViewById<TextInputLayout>(R.id.password_til)
        pTil.isErrorEnabled = false
        val password: String = findViewById<EditText>(R.id.password_et).text.toString()

        if (isNotValid(login = login, password = password, lTil = lTil, pTil = pTil)) {
            return
        }

        lifecycleScope.launch {

            val request: Deferred<AuthInfo> = async {
                api.login(
                    LoginRequest(
                        phone = lTil.prefixText.toString() + login,
                        password = password
                    )
                )
            }

            Log.i("res", request.await().toString())

        }

        this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

//        call.enqueue(
//            object : Callback<AuthInfo> {
//                override fun onResponse(call: Call<AuthInfo>, response: Response<AuthInfo>) {
//                    if (response.isSuccessful) {
//                        info = response.body()
//                        Log.i("info", info.toString())
//                    } else {
//
//                    }
//                }
//
//                override fun onFailure(call: Call<AuthInfo>, t: Throwable) {
//                }
//            }
//        )
    }
}