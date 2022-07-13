package ru.vsu.cs.gallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil.load
import org.w3c.dom.Text
import ru.vsu.cs.gallery.model.dto.response.Picture

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val picture: Picture = intent.getSerializableExtra("current") as Picture

        val iView: ImageView = findViewById(R.id.detail_image)

        Thread {
            iView.load(picture.photoUrl)
        }.start()

        val tvTitle: TextView = findViewById(R.id.detail_title);
        tvTitle.text = picture.title

        val tvDate: TextView = findViewById(R.id.detail_date)
        tvDate.text = picture.publicationDate.toString()

        val tvContent: TextView = findViewById(R.id.detail_content)
        tvContent.text = picture.content
    }

    fun goBack(view: View) {
        startActivity(
            Intent(
                this,
                GalleryFragment::class.java
            )
        )
    }
}