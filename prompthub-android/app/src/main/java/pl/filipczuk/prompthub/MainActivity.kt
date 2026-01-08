package pl.filipczuk.prompthub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import pl.filipczuk.prompthub.navigation.PromptHubNavGraph
import pl.filipczuk.prompthub.core.design_system.theme.PromptHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PromptHubTheme {
                val navController = rememberNavController()
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                        alpha = 0.4f,
                        colorFilter = ColorFilter.colorMatrix(ColorMatrix(floatArrayOf(
                            -1f, 0f, 0f, 0f, 1f,
                            0f, -1f, 0f, 0f, 1f,
                            0f, 0f, -1f, 0f, 1f,
                            0f, 0f, 0f, 1f, 0f
                        )))
                    )
                    
                    PromptHubNavGraph(navController)
                }
            }
        }
    }
}