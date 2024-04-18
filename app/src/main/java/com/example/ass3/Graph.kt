package com.example.ass3

import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp



@Composable
fun GraphScreen(viewModel: OrientationViewModel) {
    val orientationData by viewModel.data.collectAsState()
    //Log.d("main",orientationData.toString())
    val rollData = orientationData.mapIndexed { index, data ->
//        Log.d("main",data.toString())
        Point(x = index.toFloat(), y = data.roll.toFloat())
    }

    val pitchData = orientationData.mapIndexed { index, data ->
        Point(x = index.toFloat(), y = data.pitch.toFloat())
    }

    val yawData = orientationData.mapIndexed { index, data ->
        Point(x = index.toFloat(), y = data.yaw.toFloat())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StraightLinechart(rollData, "Year", "Roll")
        StraightLinechart(pitchData, "Year", "Pitch")
        StraightLinechart(yawData, "Year", "Yaw")
    }
}


@Composable
private fun StraightLinechart(pointsData: List<Point>, xAxisLabel: String, yAxisLabel: String) {
    // Ensure at least 2 points for a line chart
    if (pointsData.size < 2) {
        // Handle this case, maybe show an error message or return
        return
    }

    // Calculate min and max values for dynamic axis labels
    val minX = pointsData.minOfOrNull { it.x }?.toFloat() ?: 0f
    val maxX = pointsData.maxOfOrNull { it.x }?.toFloat() ?: 1f
    val minY = pointsData.minOfOrNull { it.y }?.toFloat() ?: 0f
    val maxY = pointsData.maxOfOrNull { it.y }?.toFloat() ?: 100f

    val xAxisData = AxisData.Builder()
        .axisStepSize(((maxX - minX) / 5).dp)  // Increased step size for wider x-axis
        .steps(5)  // Reduced number of steps for xAxis to spread out labels
        .labelData { i ->
            val step = ((maxX - minX) / 5)
            (minX + i * step).toInt().toString()
        }
        .axisLabelAngle(20f)
        .labelAndAxisLinePadding(15.dp)
        .axisLabelColor(Color.Blue)
        .axisLineColor(Color.DarkGray)
        .typeFace(Typeface.DEFAULT_BOLD)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(10)
        .labelData { i ->
            val step = ((maxY - minY) / 10)
            "${(minY + i * step)}"
        }
        .labelAndAxisLinePadding(30.dp)
        .axisLabelColor(Color.Blue)
        .axisLineColor(Color.DarkGray)
        .typeFace(Typeface.DEFAULT_BOLD)
        .build()

    val data = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(lineType = LineType.Straight(), color = Color.Blue),
                    intersectionPoint = IntersectionPoint(color = Color.Red),
                    selectionHighlightPopUp = SelectionHighlightPopUp(popUpLabel = { x, y ->
                        val xLabel = "Timestamp : ${x.toLong()}"
                        val yLabel = "Value : ${String.format("%.2f", y)}"
                        "$xLabel\n$yLabel"
                    })
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = data
    )
}
