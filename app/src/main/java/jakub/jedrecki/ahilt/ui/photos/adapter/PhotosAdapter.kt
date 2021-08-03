package jakub.jedrecki.ahilt.ui.photos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jakub.jedrecki.ahilt.databinding.ItemPhotoBinding
import jakub.jedrecki.ahilt.domain.model.PhotoItem

class PhotosAdapter(
    private val items: List<PhotoItem>
) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosAdapter.ViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.photo = items[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)
}