package pe.idat.androidproyecto.route

sealed class Rutas(val ruta: String) {
    object Home : Rutas("home")
    object Profile : Rutas("profile")
    object Favorite : Rutas("favoritos")
    object Promo : Rutas("promocion")
    object Login : Rutas("login")
    object Recuperar : Rutas("recuperar")
    object Registrar : Rutas("registrar")
    object Populares: Rutas("populares")
    object Abarrotes: Rutas("abarrotes")
    object Bebidas: Rutas("bebidas")
    object Embutidos: Rutas("embutidos")
    object Lacteos: Rutas("lacteos")
    object Licores: Rutas("licores")
    object Limpieza: Rutas("limpieza")
    object Seemore: Rutas("seemore")
    object Vegetales: Rutas("vegetales")
    object Carrito: Rutas("carrito")
    object Boleta: Rutas("boleta")

}
