package com.example.naturebubbles

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naturebubbles.ui.theme.nujnoefont

@Preview
@Composable
fun MenuScreen(
    onSettingsClick: () -> Unit = {},
    onStartClick: () -> Unit = {},
    onExitClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background2),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Nature Bubbles",
                fontFamily = nujnoefont,
                fontSize = 47.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 120.dp)
            )
            Spacer(modifier = Modifier.padding(60.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.start),
                    contentDescription = "Start Button",
                    modifier = Modifier
                        .clickable {

                            onStartClick()
                        }
                        .padding(10.dp)
                        .size(210.dp, 80.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "Settings Button",
                    modifier = Modifier
                        .clickable {

                            onSettingsClick()
                        }
                        .padding(10.dp)
                        .size(210.dp, 80.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.exit),
                    contentDescription = "Exit Button",
                    modifier = Modifier
                        .clickable {

                            onExitClick()
                        }
                        .padding(10.dp)
                        .size(210.dp, 80.dp)
                )
            }
        }
    }
}
