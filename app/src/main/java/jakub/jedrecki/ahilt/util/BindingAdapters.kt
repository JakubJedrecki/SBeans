package jakub.jedrecki.ahilt.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: ResourceString?) {
    if (errorMessage == null) {
        view.error = ""
        return
    }
    val message = errorMessage.format(view.context)

    view.error = message
}

@BindingAdapter("thumbnailUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrBlank()) {
        Glide.with(view.context).load(url)
            .transform(RoundedCorners(16))
            .into(view)
    }
}