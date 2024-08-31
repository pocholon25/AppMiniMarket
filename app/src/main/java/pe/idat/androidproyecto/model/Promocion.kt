package pe.idat.androidproyecto.model

import androidx.annotation.DrawableRes
import pe.idat.androidproyecto.R

data class Promocion(
    val nombre: String,
    val precio : Double,
    @DrawableRes val imagenRes: Int
)
val promosList = listOf(
    Promocion("Producto 1", 29.99, R.drawable.v1),
    Promocion("Producto 2", 39.99, R.drawable.v2),
    Promocion("Producto 3", 49.99, R.drawable.v3),
    Promocion("Producto 4", 59.99, R.drawable.v4),
    Promocion("Producto 5", 69.99, R.drawable.v5),
    Promocion("Producto 6", 79.99, R.drawable.v6),
    Promocion("Producto 7", 89.99, R.drawable.v1),
    Promocion("Producto 8", 99.99, R.drawable.v2),
    Promocion("Producto 9", 109.99, R.drawable.v3),
    Promocion("Producto 10", 119.99, R.drawable.v5))