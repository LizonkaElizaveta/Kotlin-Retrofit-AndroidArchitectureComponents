package stanevich.elizaveta.hoteldisplay

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class RecyclerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    var hotelList: ArrayList<Hotel> = arrayListOf()
    var URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recylcerview_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hotel = hotelList[position]
        holder.tvHotelName.text = hotel.name
        holder.tvAddressHotel.text = hotel.address
        holder.tvStarsHotel.text = hotel.stars.toString()
        if (hotel.image?.isNotEmpty() == true && hotel.image?.endsWith(".jpg") == true) {
            Glide.with(context).load(URL + hotel.image)
                .apply(RequestOptions().centerCrop())
                .into(holder.image)
        }
    }

    fun setHotelListItems(hotelList: Collection<Hotel>) {
        this.hotelList.addAll(hotelList)
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvHotelName: TextView = itemView!!.findViewById(R.id.nameOfHotel)
        val tvAddressHotel: TextView = itemView!!.findViewById(R.id.addressOfHotel)
        val tvStarsHotel: TextView = itemView!!.findViewById(R.id.starsOfHotel)
        val image: ImageView = itemView!!.findViewById(R.id.ivHotel)

    }


}