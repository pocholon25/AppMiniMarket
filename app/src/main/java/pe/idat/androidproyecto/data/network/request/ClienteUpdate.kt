package pe.idat.androidproyecto.data.network.request

data class ClienteUpdate(
    val nombre: String,
    val email: String,
    val celular: String,
    val password: String
)
