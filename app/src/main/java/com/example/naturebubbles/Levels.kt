package com.example.naturebubbles

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.example.naturebubbles.ui.theme.nujnoefont

@Preview
@Composable
fun LevelsScreen(
    onLvlClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {},
){
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
                text = "Levels",
                fontFamily = nujnoefont,
                fontSize = 47.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 100.dp)
            )
            Spacer(modifier = Modifier.padding(20.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 50.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lvl1),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {

                            onLvlClick(1)
                        }
                        .padding(10.dp)
                        .size(190.dp, 80.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.lvl2),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {

                            onLvlClick(2)
                        }
                        .padding(10.dp)
                        .size(190.dp, 80.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.lvl3),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {

                            onLvlClick(3)
                        }
                        .padding(10.dp)
                        .size(190.dp, 80.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.lvl4),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {

                            onLvlClick(4)
                        }
                        .padding(10.dp)
                        .size(190.dp, 80.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.lvl5),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {

                            onLvlClick(5)
                        }
                        .padding(10.dp)
                        .size(190.dp, 80.dp)
                )
                Spacer(modifier = Modifier.height(50.dp))
                Image(painter = painterResource(id = R.drawable.back),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            onBackClick()
                        }
                        .size(140.dp, 60.dp)
                )
            }
        }
    }
}