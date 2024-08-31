package pe.idat.androidproyecto.data.network.response

data class ProductoResponse(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: String,
    val image: String
)
