package pt.fabiomatos.weatherapp


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.fabiomatos.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {

    companion object {
        private var instance: MainActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TopContent()
                }
            }
        }
    }
}

@Composable
fun TopContent(){

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DotGraph(dots = listOf(
            Pair(100f, 100f),
            Pair(200f, 200f),
            Pair(250f, 210f),
            Pair(300f, 250f),
            Pair(400f, 280f),
            Pair(500f, 290f),
            Pair(600f, 500f),
        ))
    }
}

@Composable
fun DotGraph(dots: List<Pair<Float, Float>>) {
    Canvas(modifier = Modifier.fillMaxWidth()) {

        if (dots.size > 1) {
            for (i in 0 until dots.size - 1) {
                drawLine(
                    color = Color.Blue,
                    start = androidx.compose.ui.geometry.Offset(dots[i].first, dots[i].second),
                    end = androidx.compose.ui.geometry.Offset(dots[i + 1].first, dots[i + 1].second),
                    strokeWidth = 2.dp.toPx()
                )
            }
        }

        for ((x, y) in dots) {
            DrawDot(x, y, 10f, Color.Blue)
        }
    }
}

fun DrawScope.DrawDot(x: Float, y: Float, radius: Float, color: Color) {
    drawCircle(
        color = color,
        radius = radius,
        center = androidx.compose.ui.geometry.Offset(x, y)
    )
}


@Preview
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TopContent()
        }
    }
}