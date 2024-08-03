package pt.fabiomatos.weatherapp


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import pt.fabiomatos.weatherapp.ui.theme.WeatherAppTheme
import pt.fabiomatos.weatherapp.utils.Constants
import pt.fabiomatos.weatherapp.utils.Utils
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
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)) {
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
            .background(Color.LightGray)
    ) {
        LaunchedEffect(1) {
            viewModel.fetchWeather()
        }

        val weather by viewModel.current
        val isLoading by viewModel.isLoading


            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Column {
                    weather?.current?.let { TopContent(it) }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        DailyWeather()
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
            .height(400.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.weather), // Replace with your drawable resource
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .height(400.dp),
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
                        color = Color.Black,
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = "${String.format("%.0f", curent.temp)}º",
                        fontWeight = FontWeight.Bold,
                        fontSize = 70.sp,
                        color = Color.Black,
                        textAlign = TextAlign.End
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                )
            ) {
                Row(
                    modifier = Modifier
                        .height(270.dp)
                        .padding(top = 50.dp, start = 20.dp, end = 20.dp),
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
                            Pair(500f, 290f)
                        )
                    )
                }
            }
        }

    }
}

@Composable
fun DotGraph(dots: List<Pair<Float, Float>>) {
    Canvas(modifier = Modifier.fillMaxHeight()) {

        if (dots.size > 1) {
            for (i in 0 until dots.size - 1) {
                drawLine(
                    color = Color(0xFF2245BE),
                    start = Offset(dots[i].first, dots[i].second),
                    end = Offset(dots[i + 1].first, dots[i + 1].second),
                    strokeWidth = 4.dp.toPx()
                )
            }
        }

        for ((x, y) in dots) {
            DrawDot(x, y, 20f, Color(0xFF2245BE))
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyWeather(viewModel: BaseViewModel = viewModel()){
    val daily by viewModel.daily.observeAsState(initial = emptyList())
    Row(
        modifier = Modifier
            .fillMaxWidth()

    ) {


        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val groupedDays = Utils.GroupItemsByDay(daily).toList()
            items(groupedDays) { index ->

                Text(
                    text = Utils.formatDayWeather(index.first, Constants.MMM_DD),
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth().background(Color.White)
                ) {

                    items(index.second) { item ->

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sun),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                            Text(
                                text = "${String.format("%.0f", item.main?.temp)}º",
                                fontSize = 22.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = String.format("%02d",Utils.getHourFromDateTime(item.dtTxt!!)),
                                fontSize = 18.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun GreetingPreview() {
    val scrollState = rememberScrollState()

    WeatherAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {

                TopContent(
                    Current(temp = 22.65)
                )

                val daily = listOf(
                    ListDay(dt =1722848400, dtTxt = "2024-08-03 01:00:00"),
                    ListDay(dt =1722859200, dtTxt = "2024-08-03 04:00:00"),
                    ListDay(dt =1722870000, dtTxt = "2024-08-03 06:00:00"),
                    ListDay(dt =1722999600, dtTxt = "2024-08-03 09:00:00"),
                    ListDay(dt =1723010400, dtTxt = "2024-08-03 10:00:00"),
                    ListDay(dt =1723086000, dtTxt = "2024-08-03 15:00:00"),
                    ListDay(dt =1723096800, dtTxt = "2024-08-03 18:00:00"),
                    ListDay(dt =1723107600, dtTxt = "2024-08-03 21:00:00")
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {


                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        val groupedDays = Utils.GroupItemsByDay(daily).toList()
                        items(groupedDays) { index ->

                            Text(
                                text = Utils.formatDayWeather(index.first, Constants.MMM_DD),
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxWidth().background(Color.White)
                            ) {

                                items(index.second) { item ->

                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.sun),
                                            contentDescription = null,
                                            modifier = Modifier.size(30.dp)
                                        )
                                        Text(
                                            text = "${String.format("%.0f", item.main?.temp)}º",
                                            fontSize = 22.sp,
                                            color = Color.Black,
                                            textAlign = TextAlign.Center
                                        )
                                        Text(
                                            text = String.format("%02d",Utils.getHourFromDateTime(item.dtTxt!!)),
                                            fontSize = 18.sp,
                                            color = Color.Gray,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}