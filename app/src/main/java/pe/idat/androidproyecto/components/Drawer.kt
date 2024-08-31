package pe.idat.androidproyecto.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import pe.idat.androidproyecto.R
import pe.idat.androidproyecto.route.Rutas
import pe.idat.androidproyecto.ui.theme.Pink40
import pe.idat.androidproyecto.ui.theme.Pink80
import pe.idat.androidproyecto.ui.theme.Purple40


@Composable
fun DrawerContent(
    navController: NavController,
    drawerState: DrawerState
) {
    val coroutineScope = rememberCoroutineScope()//no se estan usando
    val context = LocalContext.current//no se estan usando

    ModalDrawerSheet {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.minimarket),
                    contentDescription = "MiniMarket Julita",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                Text(
                    text = "MiniMarket Julita",
                    color = Purple40,
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        Divider()
        DrawerItem(navController, drawerState, Rutas.Home, "Home", Icons.Default.Home, Pink80)
        DrawerItem(navController, drawerState, Rutas.Profile, "Profile", Icons.Default.Person, Pink80)
        DrawerItem(navController, drawerState, Rutas.Promo, "Promociones", Icons.Filled.StarBorder, Pink80)
        DrawerItem(navController, drawerState, Rutas.Favorite, "Favoritos", Icons.Filled.FavoriteBorder, Pink80)
    }
}

@Composable
fun DrawerItem(
    navController: NavController,
    drawerState: DrawerState,
    rutas: Rutas,
    label: String,
    icon: ImageVector,
    tint: Color,
    onClick: (() -> Unit)? = null
) {
    val coroutineScope = rememberCoroutineScope()
    NavigationDrawerItem(
        label = { Text(text = label, color = Pink40) },
        selected = false,
        icon = { Icon(imageVector = icon, contentDescription = label, tint = tint) },
        onClick = {
            coroutineScope.launch {
                navController.navigate(rutas.ruta) {
                    popUpTo(0)
                }
                drawerState.close()
                onClick?.invoke()
            }
        }
    )
}
