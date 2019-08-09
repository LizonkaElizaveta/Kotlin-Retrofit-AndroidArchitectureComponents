package stanevich.elizaveta.hoteldisplay.detail

import stanevich.elizaveta.hoteldisplay.network.HotelProperty

interface OnHotelSelected {
    fun onHotelSelected(hotelProperty: HotelProperty)
}