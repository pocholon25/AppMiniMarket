package pe.idat.androidproyecto.model

import pe.idat.androidproyecto.R

data class Lacteos(
    val imageRes: Int,
    val title: String,
    val price: String
)

val lacteos = listOf(
    Lacteos(R.drawable.l1, "Leche Entera", "S/4"),
    Lacteos(R.drawable.l2, "Yogurt Natural", "S/5"),
    Lacteos(R.drawable.l3, "Queso Fresco", "S/12"),
    Lacteos(R.drawable.l4, "Mantequilla", "S/10"),
    Lacteos(R.drawable.l5, "Queso Parmesano", "S/20"),
    Lacteos(R.drawable.l6, "Leche Descremada", "S/4"),
    Lacteos(R.drawable.l1, "Yogurt Griego", "S/7"),
    Lacteos(R.drawable.l2, "Queso Mozzarella", "S/15"),
    Lacteos(R.drawable.l3, "Leche de Almendras", "S/6"),
    Lacteos(R.drawable.l4, "Queso Cheddar", "S/18"),
    Lacteos(R.drawable.l5, "Yogurt con Frutas", "S/5"),
    Lacteos(R.drawable.l6, "Queso Brie", "S/22"),
    Lacteos(R.drawable.l1, "Leche de Soya", "S/5"),
    Lacteos(R.drawable.l2, "Crema de Leche", "S/8"),
    Lacteos(R.drawable.l3, "Queso Azul", "S/25")
)

