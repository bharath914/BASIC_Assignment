package com.bharath.basicassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.bharath.basicassignment.presentation.MyNavHost
import com.bharath.basicassignment.ui.theme.BASICAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Supabase Api keys are stored in build config file
 * so if you pulled this repository from Github then this won't work as Api Key is required
 * contact : bharathayinala@gmail.com for API keys.
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            BASICAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {

                    val navHostController = rememberNavController()
                    MyNavHost(navHostController = navHostController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BASICAssignmentTheme {
        Greeting("Android")
    }
}