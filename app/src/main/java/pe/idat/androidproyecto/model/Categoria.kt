package pe.idat.androidproyecto.model

import androidx.annotation.DrawableRes
import pe.idat.androidproyecto.R

data class Categoria(
    val nombre: String,
    @DrawableRes val imagenRes: Int
)
val items = listOf(
    Pair("Vegetales", R.drawable.frutas),
    Pair("Embutidos", R.drawable.embutidos),
    Pair("Bebidas", R.drawable.bebidas),
    Pair("Lacteos", R.drawable.lact),
    Pair("Limpieza", R.drawable.limpiesa),
    Pair("Licores", R.drawable.licores),
    Pair("Abarrotes", R.drawable.abarrotes),
    Pair("Ver más", R.drawable.vermas)
)
