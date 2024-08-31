package pe.idat.androidproyecto.model

import pe.idat.androidproyecto.R

data class Embutido(
    val imageRes: Int,
    val title: String,
    val price: String
)
val embutidos = listOf(
    Embutido(R.drawable.e1, "Jamón Inglés", "S/15"),
    Embutido(R.drawable.e2, "Salchicha Alemana", "S/12"),
    Embutido(R.drawable.e3, "Chorizo Español", "S/14"),
    Embutido(R.drawable.e4, "Mortadela", "S/10"),
    Embutido(R.drawable.e5, "Pepperoni", "S/13"),
    Embutido(R.drawable.e6, "Salami", "S/15"),
    Embutido(R.drawable.e7, "Tocino Ahumado", "S/18"),
    Embutido(R.drawable.e1, "Jamón de Pavo", "S/16"),
    Embutido(R.drawable.e2, "Salchichón", "S/11"),
    Embutido(R.drawable.e3, "Prosciutto", "S/20"),
    Embutido(R.drawable.e4, "Capicola", "S/14"),
    Embutido(R.drawable.e5, "Lomo Embuchado", "S/17"),
    Embutido(R.drawable.e6, "Jamón Serrano", "S/19"),
    Embutido(R.drawable.e7, "Longaniza", "S/12"),
    Embutido(R.drawable.e1, "Chistorra", "S/15")
)