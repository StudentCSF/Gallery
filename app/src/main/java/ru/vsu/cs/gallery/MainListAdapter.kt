package ru.vsu.cs.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.vsu.cs.gallery.model.dto.response.Picture

class MainListAdapter(
    private val context: Context,
    private val pictures: List<Picture>,
    private val inflater: LayoutInflater = LayoutInflater.from(context),
    private val onClickListener: OnPictureClickListener
) : RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    interface OnPictureClickListener {
        fun onPictureClick(picture: Picture?, position: Int)
    }

    inner class ViewHolder(
        itemView: View,
        val imageView: ImageView = itemView.findViewById(R.id.main_list_image),
        val textView: TextView = itemView.findViewById(R.id.main_list_title)
    ) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        inflater.inflate(
            R.layout.main_list_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picture: Picture = pictures[position]
        Thread {
            holder.imageView.load(picture.photoUrl)
        }.start()
        holder.textView.text = picture.title

        holder.itemView.setOnClickListener {
            onClickListener.onPictureClick(picture, position)
        }
    }

    override fun getItemCount(): Int = pictures.size
}