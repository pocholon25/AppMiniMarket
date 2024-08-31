package pe.idat.androidproyecto.model

data class Compra(
    val id: Long,
    val nombreProducto: String,
    val cantidad: Int,
    val precio: Double,
    val imagenUrl:String
)


