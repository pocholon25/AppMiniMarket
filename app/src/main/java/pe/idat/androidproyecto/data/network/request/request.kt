package pe.idat.androidproyecto.data.network.request

data class VentaRequest(
    val cliente: Cliente,
    val fecha: String,
    val detalleVenta: List<DetalleVenta>
)

data class Cliente(
    val idcliente: Long
)

data class DetalleVenta(
    val producto: Producto,
    val cantidad: Int
)

data class Producto(
    val id: Long
)
