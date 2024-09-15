package com.example.naturebubbles

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.naturebubbles.ui.theme.nujnoefont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.TimeUnit

class GameViewModelFactory(private val lvl: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(lvl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

enum class ElementType {
    FIRE, WATER, LEAF, ROCK, SNOW, EMPTY
}

fun createRandomGrid(rows: Int, cols: Int): Array<Array<ElementType>> {
    val elements = ElementType.entries.toList() - ElementType.EMPTY
    val totalCells = rows * cols

    // Ensure we have enough space for at least 3 of each element
    require(elements.size * 3 <= totalCells) { "Grid is too small to contain at least 3 of each element type" }

    // Create a list that will hold all elements to be placed
    val gridElements = mutableListOf<ElementType>()

    // Add 3 of each element to the list
    elements.forEach { element ->
        repeat(3) { gridElements.add(element) }
    }

    // Fill the rest of the grid with random elements
    while (gridElements.size < totalCells) {
        gridElements.add(elements.random())
    }

    // Shuffle the list to distribute the elements randomly
    gridElements.shuffle()

    // Create the grid and place the shuffled elements
    return Array(rows) { row ->
        Array(cols) { col ->
            gridElements.removeAt(0)
        }
    }
}


@Composable
fun GameScreen(
    lvl: Int,
    viewModel: GameViewModel = viewModel(factory = GameViewModelFactory(lvl)),
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
) {
    val grid by viewModel.grid
    val score by viewModel.score
    val timeRemaining by viewModel.timeRemaining
    val isWin by viewModel.isWin
    var isSettings by remember {
        mutableStateOf(false)
    }
    val isPaused by viewModel.isPaused

    if (isWin != null) {
        if (isWin == true) {
            WinScreen(
                lvl = lvl,
                onHome = onHomeClick,
                onNextLevel = onBackClick,
                score = score
            )
        }
        else {
            LoseScreen(
                lvl = lvl,
                onHome = onHomeClick,
                onRestart = onBackClick
            )
        }
    }
    else if (isSettings) {
        BackHandler {
            isSettings = false
            viewModel.switchPause()
        }
        SettingsScreen(
            onBackClick = {
                isSettings = false
                viewModel.switchPause()
            }
        )
    }
    else {
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
                        onClick = { viewModel.switchPause() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pause),  // Pause icon resource
                            contentDescription = "Pause",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .alpha(
                                    if (isPaused) 0.5f else 1f
                                )
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
                            modifier = Modifier.size(24.dp),
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
                        onClick = {
                            isSettings = true
                            viewModel.switchPause()
                        },
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
                                    isVisible = viewModel.isElementVisible(rowIndex, colIndex),
                                    modifier = Modifier
                                        .size(34.dp)
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
                    modifier = Modifier
                        .padding(4.dp)
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
}

@Composable
fun ElementTile(
    element: ElementType,
    isVisible: Boolean,  // Добавляем параметр для видимости
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(targetValue = if (isVisible) 1f else 0f)  // Анимация изменения размера

    AnimatedVisibility(
        visible = isVisible && element != ElementType.EMPTY,
        exit = fadeOut() + scaleOut(),  // Плавное исчезновение с уменьшением размера,
        enter = fadeIn() + scaleIn(),  // плавное появление с увеличением размера
        modifier = modifier.size((38 * scale).dp)  // Увеличиваем/уменьшаем размер
    ) {
        Image(
            painter = painterResource(
                id = when (element) {
                    ElementType.FIRE -> R.drawable.fire
                    ElementType.WATER -> R.drawable.water
                    ElementType.LEAF -> R.drawable.leaf
                    ElementType.ROCK -> R.drawable.stone
                    ElementType.SNOW -> R.drawable.snow
                    ElementType.EMPTY -> R.drawable.snow
                }
            ),
            contentDescription = "",
            modifier = Modifier
        )
    }
}

class GameViewModel(lvl: Int) : ViewModel() {
    private var gridRows: Int = when (lvl) {
        1 -> 8
        2 -> 9
        3 -> 10
        4 -> 11
        5 -> 12
        else -> 8
    }
    private var gridCols: Int = when (lvl) {
        1 -> 5
        2 -> 6
        3 -> 7
        4 -> 8
        5 -> 9
        else -> 5
    }
    private var timerDuration: Long = when (lvl) {
        1 -> 50000L
        2 -> 50000L
        3 -> 50000L
        4 -> 50000L
        5 -> 60000L
        else -> 100000L
    }

    private val _grid = mutableStateOf(createRandomGrid(gridRows, gridCols))
    val grid: State<Array<Array<ElementType>>> get() = _grid

    private val _score = mutableIntStateOf(0)
    val score: State<Int> get() = _score

    private val _timeRemaining = mutableLongStateOf(timerDuration)
    val timeRemaining: State<Long> get() = _timeRemaining

    private var visibilityGrid = mutableStateOf(Array(gridRows) { Array(gridCols) { true } })

    private val _isPaused = mutableStateOf(false)
    val isPaused: State<Boolean> get() = _isPaused

    private val _isWin = mutableStateOf(null as Boolean?)

    val isWin: State<Boolean?> get() = _isWin

    init {
        startTimer(timerDuration)
    }

    fun onElementClick(row: Int, col: Int) {
        if (!_isPaused.value) {
            removeMatchingElements(row, col)
        }
    }

    private fun removeMatchingElements(row: Int, col: Int) = viewModelScope.launch {
        val selectedType = _grid.value[row][col]
        val toRemove = mutableListOf<Pair<Int, Int>>()
        findMatchingElements(row, col, selectedType, toRemove)

        val newVisibilityGrid = visibilityGrid.value.map { it.copyOf() }.toTypedArray()
        if (toRemove.size >= 2) {
            toRemove.forEach { (r, c) ->
                newVisibilityGrid[r][c] = false
            }
            visibilityGrid.value = newVisibilityGrid
            delay(500)
            val newGrid = _grid.value.map { it.copyOf() }.toTypedArray()

            toRemove.forEach { (r, c) ->
                newGrid[r][c] =
                    ElementType.EMPTY // Помечаем элемент как EMPTY вместо перезаполнения
                newVisibilityGrid[r][c] = true
            }

            _grid.value = newGrid
            visibilityGrid.value = newVisibilityGrid

            _score.intValue += toRemove.size

            if (_grid.value.flatten().all { it == ElementType.EMPTY }
                || _score.intValue >= gridCols * gridRows / 2
            ) {
                _isWin.value = true
            }
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

    fun isElementVisible(row: Int, col: Int): Boolean {
        return visibilityGrid.value.getOrNull(row)?.getOrNull(col) ?: false
    }

    private fun startTimer(duration: Long) {
        _timeRemaining.longValue = duration
        viewModelScope.launch {
            while (_timeRemaining.longValue > 0) {
                delay(1000)
                if (!_isPaused.value) {
                    _timeRemaining.longValue -= 1000
                }
            }

            _isWin.value = _grid.value.flatten()
                .all { it == ElementType.EMPTY } || _score.intValue >= gridCols * gridRows / 2
        }
    }

    fun switchPause() {
        _isPaused.value = _isPaused.value.not()
    }
}


val Long.millisToFormatted: String
    get() = String.format(
        Locale.getDefault(),
        "%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(this) % 60,
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )