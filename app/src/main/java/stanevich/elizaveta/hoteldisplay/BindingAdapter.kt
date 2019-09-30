package stanevich.elizaveta.hoteldisplay

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import stanevich.elizaveta.hoteldisplay.network.HotelsProperty
import stanevich.elizaveta.hoteldisplay.overview.HotelAdapter
import stanevich.elizaveta.hoteldisplay.overview.HotelApiStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<HotelsProperty>?) {
    val adapter = recyclerView.adapter as HotelAdapter
    adapter.submitList(data)
}

@BindingAdapter("imgUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions().override(1000)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)

    }
}

@BindingAdapter("status")
fun bindStatus(progressBar: ProgressBar, hotelStatus: HotelApiStatus?) {
    when (hotelStatus) {
        HotelApiStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
        HotelApiStatus.DONE -> {
            progressBar.visibility = View.GONE
        }
        HotelApiStatus.ERROR -> {
            progressBar.visibility = View.GONE
        }
    }
}

@BindingAdapter("errorImage")
fun bindErrorImage(statusErrorImage: ImageView, hotelStatus: HotelApiStatus?) {
    when (hotelStatus) {
        HotelApiStatus.ERROR -> {
            statusErrorImage.visibility = View.VISIBLE
            statusErrorImage.setImageResource(R.drawable.ic_error_black)
        }
    }
}