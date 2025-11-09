// In /app/src/main/java/dev/uncognic/didilockit/MainActivity.kt

package dev.uncognic.didilockit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import dev.uncognic.didilockit.ui.home.LockScreen
import dev.uncognic.didilockit.ui.theme.DidILockItTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val statusManager = StatusManager(applicationContext)


        setContent {
            DidILockItTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {

                        NavigationBar {
                            NavigationBarItem(
                                selected = true,
                                onClick = {  },
                                icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                                label = { Text("Home") }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {

                            LockScreen(statusManager = statusManager)
                        }

                    }
                }
            }
        }
    }
}
