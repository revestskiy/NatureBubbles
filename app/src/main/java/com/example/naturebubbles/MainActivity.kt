package com.example.naturebubbles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.naturebubbles.ui.theme.NatureBubblesTheme
import com.example.naturebubbles.ui.theme.nujnoefont
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Prefs.init(application)
        SoundManager.init(application)
        setContent {
            NatureBubblesTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "loading") {
                    composable("loading") {
                        LoadingScreen(
                            onNavigate = {
                                navController.navigate("menu") {
                                    popUpTo("loading") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("menu") {
                        MenuScreen(
                            onExitClick = {
                                navController.navigate("exit")
                            },
                            onSettingsClick = {
                                navController.navigate("settings")
                            },
                            onStartClick = {
                                navController.navigate("levels")
                            }
                        )
                    }
                    composable("exit") {
                        ExitScreen(
                            onBack = navController::popBackStack
                        )
                    }
                    composable("settings") {
                        SettingsScreen(
                            onBackClick = navController::popBackStack
                        )
                    }
                    composable("levels") {
                        LevelsScreen(
                            onBackClick = navController::popBackStack,
                            onLvlClick = { lvl -> navController.navigate("game/$lvl") }
                        )
                    }
                    composable("game/{lvl}") { backStackEntry ->
                        val lvl = backStackEntry.arguments?.getString("lvl")!!.toInt()
                        GameScreen(
                            lvl = lvl,
                            onBackClick = navController::popBackStack
                        )
                    }
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        SoundManager.resumeMusic()
    }

    override fun onPause() {
        super.onPause()
        SoundManager.pauseMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundManager.onDestroy()
    }
}

@Preview
@Composable
fun LoadingScreen(onNavigate: () -> Unit = {}) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigate()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "LOADING...",
                color = Color.White,
                fontSize = 36.sp,
                fontFamily = nujnoefont,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(4f, 4f),
                        blurRadius = 8f
                    )
                )
            )
        }
    }
}