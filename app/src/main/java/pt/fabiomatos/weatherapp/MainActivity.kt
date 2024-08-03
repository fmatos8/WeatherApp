package pt.fabiomatos.weatherapp


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import pt.fabiomatos.weatherapp.ui.theme.WeatherAppTheme
import pt.fabiomatos.weatherapp.viewmodels.BaseViewModel


class MainActivity : ComponentActivity() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WeatherScreen()
                }
            }
        }
    }
}

@Composable
fun WeatherScreen(viewModel: BaseViewModel = viewModel()) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
    ) {
        LaunchedEffect(1) {
            viewModel.fetchWeather()
        }

        val weather by viewModel.current
        val isLoading by viewModel.isLoading

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()

        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Column {
                    weather?.current?.let { TopContent(it) }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("DefaultLocale")
@Composable
fun TopContent(curent: Current) {
    Box(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.weather), // Replace with your drawable resource
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Adjust the scale as needed
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
//                painter = rememberImagePainter(
//                    data = "http://openweathermap.org/img/wn/${curent.weather[0].icon}@2x.png"
//                ),
                    painter = painterResource(id = R.drawable.sun),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "It is now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = "${String.format("%.0f", curent.temp)}º",
                        fontWeight = FontWeight.Bold,
                        fontSize = 70.sp,
                        textAlign = TextAlign.End
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // Padding around the Card
                elevation = 8.dp, // Elevation of the Card
                shape = MaterialTheme.shapes.medium // Shape with rounded corners
            ) {
                Row(
                    modifier = Modifier
                        .height(270.dp)
                        .padding(top = 50.dp, start = 20.dp, end = 20.dp)
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Center
                ) {
                    DotGraph(
                        dots = listOf(
                            Pair(100f, 100f),
                            Pair(200f, 200f),
                            Pair(250f, 210f),
                            Pair(300f, 250f),
                            Pair(400f, 280f),
                            Pair(500f, 290f),
                            Pair(600f, 500f),
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun DotGraph(dots: List<Pair<Float, Float>>) {
    Canvas(modifier = Modifier.fillMaxSize()) {

        if (dots.size > 1) {
            for (i in 0 until dots.size - 1) {
                drawLine(
                    color = Color.Blue,
                    start = Offset(dots[i].first, dots[i].second),
                    end = Offset(dots[i + 1].first, dots[i + 1].second),
                    strokeWidth = 4.dp.toPx()
                )
            }
        }

        for ((x, y) in dots) {
            DrawDot(x, y, 20f, Color.Blue)
        }
    }
}

fun DrawScope.DrawDot(x: Float, y: Float, radius: Float, color: Color) {
    drawCircle(
        color = color,
        radius = radius,
        center = Offset(x, y)
    )
}


@Preview
@Composable
fun GreetingPreview() {
    val scrollState = rememberScrollState()
    WeatherAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(state = scrollState)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    TopContent(
                        Current(temp = 26.2)
                    )
                }
            }
        }
    }
}