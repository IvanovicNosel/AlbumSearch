package ivanovic.nosel.albumsearch.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import ivanovic.nosel.albumsearch.R

class AlbumAdapter : RecyclerView.Adapter<AlbumViewHolder>() {

    var albumList: List<Album> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.album_item, parent, false)
        return AlbumViewHolder(view)
    }

    override fun getItemCount(): Int = albumList.count()

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albumList[position])
    }

}

class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val artworkView: SimpleDraweeView = itemView.findViewById(R.id.album_artwork)
    private val albumNameView: TextView = itemView.findViewById(R.id.album_name)
    private val albumReleaseDateView: TextView = itemView.findViewById(R.id.album_release_date)
    private val context = itemView.context

    fun bind(album: Album) {
        itemView.setOnClickListener { _ -> displayPopUp(album.detail) }
        artworkView.setImageURI(album.summary.artWorkUrl)
        albumNameView.text = album.summary.title
        albumReleaseDateView.text = album.summary.date
    }

    private fun displayPopUp(detail: AlbumDetail) {

        val adapter = ArrayAdapter<String>(context, android.R.layout.select_dialog_item)
        adapter.add(detail.genre)
        adapter.add(detail.price)
        adapter.add(detail.currency)
        adapter.add(detail.copyright)

        AlertDialog.Builder(context)
                .setAdapter(adapter, null)
                .setPositiveButton(android.R.string.ok, null)
                .setCancelable(false)
                .create().show()

    }
}