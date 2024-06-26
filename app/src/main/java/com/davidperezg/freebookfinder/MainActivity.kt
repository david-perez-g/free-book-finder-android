package com.davidperezg.freebookfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.davidperezg.freebookfinder.ui.modules.home.HomePage
import com.davidperezg.freebookfinder.ui.modules.qrcodescanner.QrCodeScanner
import com.davidperezg.freebookfinder.ui.theme.FreebookfinderTheme
import com.davidperezg.freebookfinder.utils.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreebookfinderTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.HOME_SCREEN
                ) {
                    composable(Routes.HOME_SCREEN) {
                        HomePage(
                            onNavigate = { route ->
                                navController.navigate(route)
                            }
                        )
                    }

                    composable(Routes.QR_CODE_SCANNER) {
                        QrCodeScanner()
                    }
                }
            }
        }
    }
}
