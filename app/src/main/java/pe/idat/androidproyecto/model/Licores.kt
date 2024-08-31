package pe.idat.androidproyecto.model

import androidx.annotation.DrawableRes
import pe.idat.androidproyecto.R

data class Producto(
    val id: Int,
    val title: String,
    val price: String,
    @DrawableRes val imageRes: Int)
val productos = listOf(
    Producto(1, "Johnnie Walker Red", "S/ 180.00", R.drawable.licor10),
    Producto(2, "Jack Daniel's", "S/ 220.00", R.drawable.licor1),
    Producto(3, "Bacardi Superior", "S/ 130.00", R.drawable.licor8),
    Producto(4, "Absolut Vodka", "S/ 170.00", R.drawable.licor7),
    Producto(5, "Jose Cuervo", "S/ 150.00", R.drawable.licor1),
    Producto(6, "Havana Club", "S/ 140.00", R.drawable.licor2),
    Producto(7, "Smirnoff", "S/ 120.00", R.drawable.licor3),
    Producto(8, "Chivas Regal", "S/ 250.00", R.drawable.licor4),
    Producto(9, "Ballantine's", "S/ 200.00", R.drawable.licor6),
    Producto(10, "Jägermeister", "S/ 180.00", R.drawable.licor7),
    Producto(11, "Grey Goose", "S/ 300.00", R.drawable.licor8),
    Producto(12, "Patrón Tequila", "S/ 350.00", R.drawable.licor7),
    Producto(13, "Captain Morgan", "S/ 140.00", R.drawable.licor1),
    Producto(14, "Ciroc", "S/ 280.00", R.drawable.licor2),
    Producto(15, "Hennessy", "S/ 400.00", R.drawable.licor3)

)