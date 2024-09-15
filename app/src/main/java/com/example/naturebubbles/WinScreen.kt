package com.example.naturebubbles

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
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
fun WinScreen(
    lvl: Int = 1,
    score: Int = 0,
    onHome: () -> Unit = {},
    onNextLevel: () -> Unit = {},
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
            // Звезды
            Image(
                painter = painterResource(id = R.drawable.zvezdi),
                contentDescription = "Stars",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(260.dp,100.dp)
            )
            Text(
                text = "WIN",
                fontFamily = nujnoefont,
                fontSize = 66.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(4.dp)
            )

            // Level 1 Completed текст
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Level $lvl Completed",
                fontFamily = nujnoefont,
                fontSize = 60.sp,
                color = Color.White
            )

            // Coins ближе к Level 1 Completed
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .paint(
                        painter = painterResource(id = R.drawable.red_bg),
                        contentScale = ContentScale.Crop
                    )
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_coin), // Coin image resource
                    contentDescription = "Coin",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))  // Small space between coin and score
                Text(
                    text = "$score",
                    style = MaterialTheme.typography.titleLarge.copy(fontFamily = nujnoefont),
                    color = Color.White,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Next Level Button
            Image(
                painter = painterResource(id = R.drawable.nextlvl),
                contentDescription = "Next Level Button",
                modifier = Modifier
                    .clickable {
                        onNextLevel()
                    }
                    .size(260.dp, 90.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Home Button
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
