package ru.vsu.cs.gallery

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import coil.load
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsu.cs.gallery.config.AppConfig
import ru.vsu.cs.gallery.model.dto.response.User

class ProfileFragment : AppCompatActivity(), ExitDialogFragment.ExitDialogFragmentListener {
    private var dialog: DialogFragment? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val sharPref = getSharedPreferences(
            AppConfig.APP_PREFERENCES,
            MODE_PRIVATE
        )

        if (!sharPref.contains(AppConfig.TOKEN) || !sharPref.contains(AppConfig.USER)) {
            this.startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                 )
            )
            this.finish()
            return
        }

        val nameTv: TextView = findViewById(R.id.profile_name)
        val aboutTv: TextView = findViewById(R.id.profile_about)
        val cityEt: EditText = findViewById(R.id.profile_city)
        val phoneEt: EditText = findViewById(R.id.profile_phone)
        val emailEt: EditText = findViewById(R.id.profile_email)
        val imageView: ImageView = findViewById(R.id.profile_avatar)

        val user: User = Gson().fromJson(
            sharPref.getString(AppConfig.USER, "").toString(), User::class.java
        )

        nameTv.text = "${user.firstName} ${user.lastName}"
        aboutTv.text = "\"" + user.about + "\""
        cityEt.setText(user.city)
        phoneEt.setText(user.phone)
        emailEt.setText(user.email)
        imageView.load(user.avatar)

        dialog = ExitDialogFragment()
    }

    private fun logout() {
        val sharPref = getSharedPreferences(
            AppConfig.APP_PREFERENCES,
            MODE_PRIVATE
        )

        val userApi = AppConfig.USER_API

        val param: String = sharPref.getString(AppConfig.TOKEN, "").toString()
        val call: Call<Void> = userApi.logout(param)

        call.enqueue(
            object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (!response.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            R.string.exit_failed,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    sharPref.edit()
                        .apply {
                            remove(AppConfig.TOKEN)
                            remove(AppConfig.USER)
                            apply()
                        }
                    Log.i("logout", response.code().toString())
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        R.string.exit_failed_by_inet,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    fun showDialog(view: View) {
        dialog?.show(supportFragmentManager, "ExitDialogFragment")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment?) {
        logout()
        startActivity(
            Intent(
                this,
                LoginActivity::class.java
            )
        )
        this.finish()
    }
}