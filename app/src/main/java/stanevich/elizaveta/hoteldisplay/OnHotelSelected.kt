package stanevich.elizaveta.hoteldisplay

import stanevich.elizaveta.hoteldisplay.network.HotelProperty

interface OnHotelSelected {
    fun onHotelSelected(hotelProperty: HotelProperty)
}