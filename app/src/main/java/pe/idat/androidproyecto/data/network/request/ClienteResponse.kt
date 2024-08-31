package pe.idat.androidproyecto.data.network.request

data class ClienteResponse(
    val idcliente: Long,
    val nombre: String,
    val email: String,
    val celular: String,
    val password: String
)

