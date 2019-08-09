package stanevich.elizaveta.hoteldisplay

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import stanevich.elizaveta.hoteldisplay.detail.OnHotelSelected
import stanevich.elizaveta.hoteldisplay.network.HotelProperty


class RecyclerViewAdapter(var listener: OnHotelSelected? = null) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var hotelPropertyList: ArrayList<HotelProperty> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recylcerview_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hotelPropertyList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hotel = hotelPropertyList[position]
        holder.tvHotelName.text = hotel.name
        holder.tvAddressHotel.text = hotel.address
        holder.tvStarsHotel.rating = hotel.stars
        holder.itemView.setOnClickListener {
            listener?.onHotelSelected(hotel)
        }
    }

    fun addHotelListItems(hotelPropertyList: Collection<HotelProperty>) {
        this.hotelPropertyList.addAll(hotelPropertyList)
    }


    fun sort(context: Context) {
        Toast.makeText(context, "Tetx", Toast.LENGTH_SHORT).show()
//        hotelPropertyList.sortBy { it.distance }
//        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvHotelName: TextView = itemView!!.findViewById(R.id.nameOfHotel)
        val tvAddressHotel: TextView = itemView!!.findViewById(R.id.addressOfHotel)
        val tvStarsHotel: RatingBar = itemView!!.findViewById(R.id.starsOfHotel)
    }


}