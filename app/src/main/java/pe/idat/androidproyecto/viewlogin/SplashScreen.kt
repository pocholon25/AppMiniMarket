package pe.idat.androidproyecto.viewlogin

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import pe.idat.androidproyecto.R
import pe.idat.androidproyecto.route.Rutas

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.minimark), // Cambia la imagen según tu recurso
            contentDescription = "Logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }

    // Navegación con un efecto lanzado para mostrar la pantalla por un tiempo específico
    LaunchedEffect(Unit) {
        delay(5000) // Espera 3 segundos
        navController.navigate(Rutas.Login.ruta) {
            popUpTo(Rutas.Splash.ruta) { inclusive = true } // Elimina la Splash de la pila de navegación
        }
    }
}