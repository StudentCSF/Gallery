package ru.vsu.cs.gallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsu.cs.gallery.MainListAdapter.OnPictureClickListener
import ru.vsu.cs.gallery.config.AppConfig
import ru.vsu.cs.gallery.model.dto.response.Picture

class GalleryFragment : AppCompatActivity() {

    private var pictures: List<Picture> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

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

        val call: Call<List<Picture>> = AppConfig.PICTURE_API.getPictures(
            sharPref.getString(AppConfig.TOKEN, "")
                .toString()
        )

        val rView: RecyclerView = findViewById(R.id.main_list)

        rView.adapter = MainListAdapter(
            context = this,
            pictures = pictures,
            onClickListener = object : OnPictureClickListener {
                override fun onPictureClick(picture: Picture?, position: Int) {

                }
            }
        )

        call.enqueue(
            object : Callback<List<Picture>> {
                override fun onResponse(
                    call: Call<List<Picture>>,
                    response: Response<List<Picture>>
                ) {
                    if (response.isSuccessful) {
                        pictures = response.body()!!
                        Log.i("picture", pictures.toString())
                        rView.adapter = MainListAdapter(
                            context = applicationContext,
                            pictures = pictures,
                            onClickListener = object : OnPictureClickListener {
                                override fun onPictureClick(picture: Picture?, position: Int) {
                                    startActivity(
                                        Intent(
                                            applicationContext,
                                            DetailActivity::class.java
                                        ).apply {
                                            putExtra("current", picture)
                                        }
                                    )
                                }
                            })
                    } else {
                        Log.d("picture", response.code().toString())
                    }
                }

                override fun onFailure(call: Call<List<Picture>>, t: Throwable) {
                    Log.e("picture", t.message.toString())
                }

            }
        )
    }
}
