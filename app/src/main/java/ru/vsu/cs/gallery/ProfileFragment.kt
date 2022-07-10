package ru.vsu.cs.gallery

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import coil.load
import com.google.gson.Gson
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

        if (!sharPref.contains(AppConfig.TOKEN)) {
            this.startActivity(
                Intent(this, LoginActivity::class.java)
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
        getSharedPreferences(
            AppConfig.APP_PREFERENCES,
            MODE_PRIVATE
        ).edit().apply {
            remove(AppConfig.TOKEN)
            remove(AppConfig.USER)
            apply()
        }

    }

    fun showDialog(view: View) {
        dialog?.show(supportFragmentManager, "ExitDialogFragment")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment?) {
        logout()
        startActivity(
            Intent(this, LoginActivity::class.java)
        )
        this.finish()
    }
}