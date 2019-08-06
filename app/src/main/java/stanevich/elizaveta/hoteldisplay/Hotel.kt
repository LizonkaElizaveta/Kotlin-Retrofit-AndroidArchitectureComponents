package stanevich.elizaveta.hoteldisplay

data class Hotel(
    var id: String,
    var name: String,
    var address: String,
    var stars: Float,
    var distance: Float,
    var suites_availability: String,
    var image: String?
)