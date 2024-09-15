package com.example.naturebubbles

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.naturebubbles.ui.theme.nujnoefont
import java.util.Locale
import java.util.concurrent.TimeUnit

enum class ElementType {
    FIRE, WATER, LEAF, ROCK, SNOW
}

// Function to create a random game grid
fun createRandomGrid(rows: Int, cols: Int): Array<Array<ElementType>> {
    val elements = ElementType.entries.toTypedArray()
    return Array(rows) {
        Array(cols) {
            elements.random()
        }
    }
}

@Composable
fun GameScreen(lvl: Int, viewModel: GameViewModel = viewModel(), onBackClick: () -> Unit = {}) {
    val grid by viewModel.grid
    val score by viewModel.score
    val timeRemaining by viewModel.timeRemaining

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background2),
                contentScale = ContentScale.Crop
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Display Score
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { /* Handle pause action */ },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_pause),  // Pause icon resource
                        contentDescription = "Pause",
                        tint = Color.Unspecified
                    )
                }

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

                IconButton(
                    onClick = { /* Handle settings action */ },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),  // Settings icon resource
                        contentDescription = "Settings",
                        tint = Color.Unspecified
                    )
                }
            }
        }
        // Game Grid
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column {
                grid.forEachIndexed { rowIndex, row ->
                    Row {
                        row.forEachIndexed { colIndex, element ->
                            ElementTile(
                                element = element,
                                modifier = Modifier
                                    .size(38.dp)
                                    .padding(2.dp)
                                    .clickable {
                                        viewModel.onElementClick(rowIndex, colIndex)
                                    }
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(10.dp)
                .paint(
                    painterResource(id = R.drawable.bg_stone),
                    contentScale = ContentScale.Crop
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                timeRemaining.millisToFormatted,
                color = Color.White,
                modifier = Modifier.padding(4.dp)
                    .padding(top = 7.dp),
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Red, // Красная обводка
                        offset = Offset(1f, 1f),
                        blurRadius = 4f
                    )
                ),
                fontFamily = nujnoefont,
                fontSize = 28.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ElementTile(element: ElementType, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(
            id = when (element) {
                ElementType.FIRE -> R.drawable.fire
                ElementType.WATER -> R.drawable.water
                ElementType.LEAF -> R.drawable.leaf
                ElementType.ROCK -> R.drawable.stone
                ElementType.SNOW -> R.drawable.snow
            }
        ),
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.FillWidth
    )
}

class GameViewModel : ViewModel() {
    private val _grid = mutableStateOf(createRandomGrid(8, 6))
    val grid: State<Array<Array<ElementType>>> get() = _grid

    private val _score = mutableIntStateOf(0)
    val score: State<Int> get() = _score

    private val _timeRemaining = mutableLongStateOf(60000L)
    val timeRemaining: State<Long> get() = _timeRemaining

    private var timer: CountDownTimer? = null

    init {
        startTimer()
    }

    fun onElementClick(row: Int, col: Int) {
        // Add logic to check and remove adjacent elements of the same type
        removeMatchingElements(row, col)
    }

    private fun removeMatchingElements(row: Int, col: Int) {
        val selectedType = _grid.value[row][col]
        val toRemove = mutableListOf<Pair<Int, Int>>()
        findMatchingElements(row, col, selectedType, toRemove)

        if (toRemove.size >= 2) {
            toRemove.forEach { (r, c) ->
                _grid.value[r][c] = ElementType.entries.toTypedArray().random()
            }
            _score.intValue += toRemove.size
        }
    }

    private fun findMatchingElements(
        row: Int, col: Int, type: ElementType,
        toRemove: MutableList<Pair<Int, Int>>,
        visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    ) {
        if (row !in _grid.value.indices || col !in _grid.value[0].indices || visited.contains(row to col)) return
        if (_grid.value[row][col] != type) return

        visited.add(row to col)
        toRemove.add(row to col)

        // Check adjacent cells
        findMatchingElements(row - 1, col, type, toRemove, visited)
        findMatchingElements(row + 1, col, type, toRemove, visited)
        findMatchingElements(row, col - 1, type, toRemove, visited)
        findMatchingElements(row, col + 1, type, toRemove, visited)
    }

    private fun startTimer() {
        timer = object : CountDownTimer(60000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                _timeRemaining.longValue = millisUntilFinished
            }

            override fun onFinish() {
                // Handle end of game
            }
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}

val Long.millisToFormatted: String
    get() = String.format(
        Locale.getDefault(),
        "%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(this) % 60,
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )