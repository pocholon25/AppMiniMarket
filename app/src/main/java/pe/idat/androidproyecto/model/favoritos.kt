package pe.idat.androidproyecto.model

import pe.idat.androidproyecto.R

data class Promo(
    val imageRes: Int,
    val title: String,
    val price: String
)

val promo = listOf(
    Promo(R.drawable.v2, "Descuento en Panes", "S/8"),
    Promo(R.drawable.e3, "Oferta en Leche", "S/5"),
    Promo(R.drawable.licor3, "2x1 en Yogur", "S/7"),
    Promo(R.drawable.licor8, "10% en Quesos", "S/15"),
    Promo(R.drawable.l3, "Pack de Embutidos", "S/25"),
    Promo(R.drawable.l5, "Descuento en Pastas", "S/12"),
    Promo(R.drawable.v6, "Ofertas en Aceites", "S/10"),
    Promo(R.drawable.e5, "Compra 1 y Lleva 2", "S/9"),
    Promo(R.drawable.licor9, "15% en Arroz", "S/14"),
    Promo(R.drawable.v6, "Combo de Salsas", "S/18")
)

