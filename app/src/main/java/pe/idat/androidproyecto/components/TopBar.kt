package pe.idat.androidproyecto.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.idat.androidproyecto.R
import pe.idat.androidproyecto.ui.theme.Pink40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    onMenuClick: () -> Unit,
    onAction1Click: () -> Unit, // Cambiado a () -> Unit
    onAction2Click: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "MiniMarket Julita", fontSize = 20.sp) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Pink40,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = onAction2Click) {
                Icon(
                    painter = painterResource(id = R.drawable.carro),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = onAction1Click) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Salir de la aplicacion",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
    )
}