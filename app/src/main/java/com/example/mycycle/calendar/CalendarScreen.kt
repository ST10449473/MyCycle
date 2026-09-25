package com.example.mycycle.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mycycle.api.RetrofitClient
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarScreen(
    userId: Int,
    onBack: () -> Unit
) {
    var periods by remember {
        mutableStateOf<List<CalendarPeriod>>(emptyList())
    }

    var message by remember {
        mutableStateOf("Loading your cycle history...")
    }

    var prediction by remember {
        mutableStateOf("")
    }

    var statistics by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    fun formatDate(date: Calendar): String {
        val formatter = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        )

        return formatter.format(date.time)
    }

    fun calculateStatistics() {

        if (periods.isEmpty()) {
            statistics = ""
            return
        }

        try {
            val formatter = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            )

            val sortedPeriods = periods.sortedBy {
                it.startDate.take(10)
            }

            val periodLengths = sortedPeriods.mapNotNull { period ->
                val start = formatter.parse(
                    period.startDate.take(10)
                )

                val end = formatter.parse(
                    period.endDate.take(10)
                )

                if (start != null && end != null) {
                    val difference =
                        end.time - start.time

                    val days =
                        (difference /
                                (1000L * 60 * 60 * 24)).toInt() + 1

                    if (days > 0) days else null
                } else {
                    null
                }
            }

            val averagePeriodLength =
                if (periodLengths.isNotEmpty()) {
                    periodLengths.average()
                } else {
                    0.0
                }

            val cycleLengths = mutableListOf<Int>()

            for (i in 1 until sortedPeriods.size) {

                val previous =
                    formatter.parse(
                        sortedPeriods[i - 1]
                            .startDate
                            .take(10)
                    )

                val current =
                    formatter.parse(
                        sortedPeriods[i]
                            .startDate
                            .take(10)
                    )

                if (previous != null && current != null) {

                    val difference =
                        current.time - previous.time

                    val days =
                        (difference /
                                (1000L * 60 * 60 * 24)).toInt()

                    if (days > 0) {
                        cycleLengths.add(days)
                    }
                }
            }

            val averageCycleLength =
                if (cycleLengths.isNotEmpty()) {
                    cycleLengths.average()
                } else {
                    0.0
                }

            statistics =
                "Periods recorded: ${periods.size}\n" +
                        "Average period length: " +
                        if (averagePeriodLength > 0) {
                            String.format(
                                Locale.getDefault(),
                                "%.1f days",
                                averagePeriodLength
                            )
                        } else {
                            "Not enough data"
                        } +
                        "\nAverage cycle length: " +
                        if (averageCycleLength > 0) {
                            String.format(
                                Locale.getDefault(),
                                "%.1f days",
                                averageCycleLength
                            )
                        } else {
                            "Not enough data"
                        }

        } catch (e: Exception) {

            statistics =
                "Unable to calculate statistics."
        }
    }

    fun calculatePrediction() {

        if (periods.size < 2) {
            prediction =
                "Add another period entry to calculate a prediction."
            return
        }

        try {

            val formatter = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            )

            val sortedPeriods = periods.sortedBy {
                it.startDate.take(10)
            }

            val previousDate =
                formatter.parse(
                    sortedPeriods[sortedPeriods.size - 2]
                        .startDate
                        .take(10)
                )

            val latestDate =
                formatter.parse(
                    sortedPeriods.last()
                        .startDate
                        .take(10)
                )

            if (previousDate == null || latestDate == null) {
                prediction =
                    "Unable to calculate prediction."
                return
            }

            val difference =
                latestDate.time - previousDate.time

            val cycleLength =
                (difference /
                        (1000L * 60 * 60 * 24)).toInt()

            if (cycleLength <= 0) {
                prediction =
                    "Unable to calculate prediction."
                return
            }

            val nextPeriod =
                Calendar.getInstance()

            nextPeriod.time = latestDate

            nextPeriod.add(
                Calendar.DAY_OF_YEAR,
                cycleLength
            )

            prediction =
                "Estimated next period: " +
                        formatDate(nextPeriod)

        } catch (e: Exception) {

            prediction =
                "Unable to calculate prediction."
        }
    }

    LaunchedEffect(userId) {

        if (userId <= 0) {

            message =
                "User session is invalid."

            isLoading = false

            return@LaunchedEffect
        }

        try {

            val response =
                RetrofitClient.api.getPeriods(userId)

            if (response.isSuccessful) {

                val data =
                    response.body() ?: emptyList()

                periods = data.map {
                    CalendarPeriod(
                        startDate = it.startDate,
                        endDate = it.endDate
                    )
                }

                if (periods.isEmpty()) {

                    message =
                        "No period entries found."

                } else {

                    message = ""

                    calculatePrediction()
                    calculateStatistics()
                }

            } else {

                message =
                    "Unable to load your cycle history."
            }

        } catch (e: Exception) {

            message =
                e.message
                    ?: "Unable to connect to the server."

        } finally {

            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Cycle Calendar",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        if (isLoading) {

            Text(
                text = message
            )

        } else if (periods.isNotEmpty()) {

            Text(
                text = "Your saved periods:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            periods.forEachIndexed { index, period ->

                Text(
                    text =
                        "${index + 1}. " +
                                "${period.startDate.take(10)} " +
                                "to " +
                                "${period.endDate.take(10)}"
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }

            if (prediction.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = prediction,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            if (statistics.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Cycle Statistics",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = statistics
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text =
                    "Predictions are estimates based on " +
                            "previous cycle data. They are not medically " +
                            "accurate and should not be used as contraception."
            )

        } else {

            Text(
                text = message
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Back to Home")
        }
    }
}

data class CalendarPeriod(
    val startDate: String,
    val endDate: String
)