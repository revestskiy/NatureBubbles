package com.example.naturebubbles

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naturebubbles.ui.theme.nujnoefont

@Preview
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {},
) {
    var musicVolume by remember { mutableFloatStateOf(Prefs.musicVolume) }
    var soundEffectsVolume by remember { mutableFloatStateOf(Prefs.soundVolume) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background2),
                contentScale = ContentScale.Crop
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 40.dp)
                .paint(
                    painter = painterResource(id = R.drawable.backgroundsettings),
                    contentScale = ContentScale.FillWidth
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                Spacer(modifier = Modifier.height(160.dp))
                Text(
                    text = "OPTIONS",
                    fontFamily = nujnoefont,
                    fontSize = 38.sp,
                    color = Color(0xffFFA000),
                    modifier = Modifier, // Добавлено больше отступов вниз
                )

                Text(
                    text = "AUDIO",
                    fontFamily = nujnoefont,
                    fontSize = 38.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 32.dp), // Добавлено больше отступов вниз
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Red, // Красная обводка
                            offset = Offset(1f, 1f),
                            blurRadius = 4f
                        )
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "MUSIC",
                        fontFamily = nujnoefont,
                        fontSize = 28.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Red, // Красная обводка
                                offset = Offset(1f, 1f),
                                blurRadius = 4f
                            )
                        )
                    )

                    Slider(
                        value = musicVolume,
                        onValueChange = {
                            musicVolume = it
                            Prefs.musicVolume = it
                            SoundManager.setMusicVolume()
                        },
                        valueRange = 0f..1f,
                        modifier = Modifier.weight(2f),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Gray,
                            activeTrackColor = Color.Red,
                            inactiveTrackColor = Color.DarkGray
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SOUND",
                        fontFamily = nujnoefont,
                        fontSize = 28.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Red, // Красная обводка
                                offset = Offset(1f, 1f),
                                blurRadius = 4f
                            )
                        )
                    )

                    Slider(
                        value = soundEffectsVolume,
                        onValueChange = {
                            soundEffectsVolume = it
                            Prefs.soundVolume = it
                            SoundManager.setSoundVolume()
                        },
                        valueRange = 0f..1f,
                        modifier = Modifier.weight(2f),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Gray,
                            activeTrackColor = Color.Red,
                            inactiveTrackColor = Color.DarkGray
                        )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .clickable {
                            onBackClick()
                        }
                        .size(150.dp, 80.dp)
                        .padding(8.dp)
                )
            }
        }
    }
}
