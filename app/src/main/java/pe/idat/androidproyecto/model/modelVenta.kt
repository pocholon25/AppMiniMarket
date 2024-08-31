package pe.idat.androidproyecto.model

data class BoletaVentaUI(
    val idVenta: Int,
    val fecha: String,
    val importe: Double,
    val clienteNombre: String,
    val productos: List<ProductoBoletaUI>
)

data class ProductoBoletaUI(
    val nombre: String,
    val cantidad: Int,
    val subtotal: Double
)