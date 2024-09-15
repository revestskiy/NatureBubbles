package com.example.naturebubbles

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naturebubbles.ui.theme.nujnoefont


@Preview
@Composable
fun ExitScreen(
    onBack: () -> Unit = {},
) {
    val activity = activity
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
                )
                .align(Alignment.Center)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "EXIT",
                    fontFamily = nujnoefont,
                    fontSize = 38.sp,
                    color = Color(0xffFFA000),
                    modifier = Modifier.padding(top = 48.dp),
                )

                Text(
                    text = "ARE YOU SURE?",
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

                Spacer(modifier = Modifier.padding(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.no),
                        contentDescription = "",
                        modifier = Modifier
                            .clickable {
                                onBack()
                            }
                            .padding(10.dp)
                            .size(100.dp, 80.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.yes),
                        contentDescription = "",
                        modifier = Modifier
                            .clickable {
                                activity.finish()
                            }
                            .padding(10.dp)
                            .size(100.dp, 80.dp)
                    )
                }
            }
        }
    }
}

val activity
    @Composable
    get() = LocalContext.current as ComponentActivity