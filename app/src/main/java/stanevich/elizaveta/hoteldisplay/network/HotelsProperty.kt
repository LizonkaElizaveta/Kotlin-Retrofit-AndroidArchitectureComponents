package stanevich.elizaveta.hoteldisplay.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelsProperty(
    var id: String,
    var name: String,
    var address: String,
    var stars: Float,
    var distance: Float,
    var suites_availability: String,
    val imgSrcUrl: String?
) : Parcelable