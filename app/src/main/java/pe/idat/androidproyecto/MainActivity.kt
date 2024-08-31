package pe.idat.androidproyecto

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pe.idat.androidproyecto.iu.AbarrotesScreen
import pe.idat.androidproyecto.iu.BebidasScreen
import pe.idat.androidproyecto.iu.EmbutidosScreen
import pe.idat.androidproyecto.iu.LacteosScreen
import pe.idat.androidproyecto.iu.LicoresScreen
import pe.idat.androidproyecto.iu.LimpiezaScreen
import pe.idat.androidproyecto.iu.SeemoreScreen
import pe.idat.androidproyecto.iu.VegetalesScreen
import pe.idat.androidproyecto.components.AppTopBar
import pe.idat.androidproyecto.components.DrawerContent
import pe.idat.androidproyecto.route.Rutas
import pe.idat.androidproyecto.ui.theme.AndroidProyectoTheme
import pe.idat.androidproyecto.viewhome.BoletaVentaScreen
import pe.idat.androidproyecto.viewhome.CarritoScreen
import pe.idat.androidproyecto.viewhome.FavoriteScreen
import pe.idat.androidproyecto.viewhome.HomeScreen
import pe.idat.androidproyecto.viewhome.PopularesScreen
import pe.idat.androidproyecto.viewhome.ProfileScreen
import pe.idat.androidproyecto.viewhome.PromoScreen
import pe.idat.androidproyecto.viewlogin.LoginScreen
import pe.idat.androidproyecto.viewlogin.RecuperarScreen
import pe.idat.androidproyecto.viewlogin.RegistrarScreen
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidProyectoTheme {

                // Navigation
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val coroutineScope = rememberCoroutineScope()
                val authViewModel: AuthViewModel = hiltViewModel() // Instancia compartida del ViewModel

                // Track current destination
                var currentDestination by remember { mutableStateOf<String?>(null) }

                LaunchedEffect(navController) {
                    navController.addOnDestinationChangedListener { _, destination, _ ->
                        currentDestination = destination.route
                        Log.d("NavigationDebug", "Updated Destination: $currentDestination")
                    }
                }
                val context = LocalContext.current
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent(navController = navController, drawerState = drawerState)
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            Log.d("NavigationDebug", "Top Bar Check - Current Destination: $currentDestination")
                            if (currentDestination !in listOf(
                                    Rutas.Login.ruta,
                                    Rutas.Registrar.ruta,
                                    Rutas.Recuperar.ruta
                                ) && currentDestination != null
                            ) {
                                AppTopBar(
                                    onMenuClick = {
                                        coroutineScope.launch { drawerState.open() }
                                    },
                                    onAction1Click = {
                                        authViewModel.logout()
                                        navController.navigate(Rutas.Login.ruta) {
                                            popUpTo(Rutas.Home.ruta) { inclusive = true }
                                        }
                                    }
                                    ,
                                    onAction2Click = {
                                        navController.navigate(Rutas.Carrito.ruta)
                                    }
                                )
                            }
                        },
                        content = { innerPadding ->
                            NavHost(
                                navController = navController,
                                startDestination = Rutas.Login.ruta,
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize()
                            ) {
                                composable(Rutas.Login.ruta) {
                                    LoginScreen(navController, authViewModel) }
                                composable(Rutas.Registrar.ruta) { RegistrarScreen(navController,authViewModel) }
                                composable(Rutas.Recuperar.ruta) { RecuperarScreen(navController) }
                                composable(Rutas.Home.ruta) { HomeScreen(navController,authViewModel) }
                                composable(Rutas.Profile.ruta) { ProfileScreen(navController,authViewModel) }
                                composable(Rutas.Promo.ruta) {
                                    PromoScreen(navController, authViewModel) }
                                composable(Rutas.Favorite.ruta) {
                                    FavoriteScreen(navController, authViewModel) }
                                composable(Rutas.Populares.ruta) {
                                    PopularesScreen(navController, authViewModel) }
                                composable(Rutas.Abarrotes.ruta) {
                                    AbarrotesScreen(navController, authViewModel) }
                                composable(Rutas.Bebidas.ruta) {
                                    BebidasScreen(navController, authViewModel) }
                                composable(Rutas.Embutidos.ruta) {
                                    EmbutidosScreen(navController, authViewModel) }
                                composable(Rutas.Lacteos.ruta) {
                                    LacteosScreen(navController, authViewModel) }
                                composable(Rutas.Licores.ruta) {
                                    LicoresScreen(navController, authViewModel) }
                                composable(Rutas.Limpieza.ruta) {
                                    LimpiezaScreen(navController, authViewModel) }
                                composable(Rutas.Seemore.ruta) {
                                    SeemoreScreen(navController, authViewModel) }
                                composable(Rutas.Vegetales.ruta) {
                                    VegetalesScreen(navController, authViewModel) }
                                composable(Rutas.Carrito.ruta) {
                                    CarritoScreen(navController, authViewModel) }
                                composable(Rutas.Boleta.ruta) {
                                    BoletaVentaScreen(navController, authViewModel)
                                }
                            }
                        },
                        floatingActionButton = {
                            if (currentDestination !in listOf(
                                    Rutas.Login.ruta,
                                    Rutas.Registrar.ruta,
                                    Rutas.Recuperar.ruta
                                ) && currentDestination != null
                            ) {
                                FloatingActionButtonHome(navController = navController)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FloatingActionButtonHome(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigate(Rutas.Home.ruta) {
                popUpTo(Rutas.Home.ruta) { inclusive = true }
            }
        },
        containerColor = Color.Transparent,
        contentColor = Color.Unspecified,
        content = {
            Box(
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.doble),
                    contentDescription = "Go to Home",
                    tint = Color.Unspecified
                )
            }
        }
    )
}
