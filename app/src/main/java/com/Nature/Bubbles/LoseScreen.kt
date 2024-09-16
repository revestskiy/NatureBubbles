package com.Nature.Bubbles

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.Nature.Bubbles.ui.theme.nujnoefont

@Preview
@Composable
fun LoseScreen(
    lvl: Int = 1,
    onRestart: () -> Unit = {},
    onHome: () -> Unit = {},
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
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "LOSE",
                fontFamily = nujnoefont,
                fontSize = 66.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(4.dp)
            )


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Level $lvl",
                fontFamily = nujnoefont,
                fontSize = 60.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Time is up",
                fontFamily = nujnoefont,
                fontSize = 60.sp,
                color = Color.White
            )


            Spacer(modifier = Modifier.padding(top = 150.dp))

            Image(
                painter = painterResource(id = R.drawable.repeat),
                contentDescription = "Next Level Button",
                modifier = Modifier
                    .clickable {
                        onRestart()
                    }
                    .size(260.dp, 90.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))


            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Home Button",
                modifier = Modifier
                    .clickable {
                        onHome()
                    }
                    .size(160.dp, 80.dp)
            )
        }
    }
}