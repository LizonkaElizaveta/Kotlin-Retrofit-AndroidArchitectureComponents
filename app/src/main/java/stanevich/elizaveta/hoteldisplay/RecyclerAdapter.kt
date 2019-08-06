package stanevich.elizaveta.hoteldisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    private var hotelList: ArrayList<Hotel> = arrayListOf()

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
    }

    fun setHotelListItems(hotelList: Collection<Hotel>) {
        this.hotelList.addAll(hotelList)
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvHotelName: TextView = itemView!!.findViewById(R.id.nameOfHotel)
        val tvAddressHotel: TextView = itemView!!.findViewById(R.id.addressOfHotel)
        val tvStarsHotel: TextView = itemView!!.findViewById(R.id.starsOfHotel)
    }


}