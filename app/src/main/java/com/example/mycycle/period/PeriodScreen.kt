package com.example.mycycle.period

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mycycle.api.PeriodRequest
import com.example.mycycle.api.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun PeriodScreen(
    userId: Int,
    onBack: () -> Unit
) {
    var startDate by remember {
        mutableStateOf("")
    }

    var endDate by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Track My Period",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = startDate,
            onValueChange = {
                startDate = it
                message = ""
            },
            label = {
                Text("Start Date (YYYY-MM-DD)")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = endDate,
            onValueChange = {
                endDate = it
                message = ""
            },
            label = {
                Text("End Date (YYYY-MM-DD)")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (message.isNotEmpty()) {

            Text(
                text = message,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        Button(
            onClick = {

                if (userId <= 0) {
                    message = "User session is invalid. Please login again."
                    return@Button
                }

                if (startDate.isBlank() || endDate.isBlank()) {
                    message = "Please enter both dates."
                    return@Button
                }

                isLoading = true
                message = ""

                scope.launch {

                    try {

                        val response =
                            RetrofitClient.api.createPeriod(
                                PeriodRequest(
                                    userId = userId,
                                    startDate = "${startDate}T00:00:00",
                                    endDate = "${endDate}T00:00:00"
                                )
                            )

                        if (response.isSuccessful) {

                            message =
                                "Period entry saved successfully."

                            startDate = ""
                            endDate = ""

                        } else {

                            message =
                                response.errorBody()?.string()
                                    ?: "Unable to save period."
                        }

                    } catch (e: Exception) {

                        message =
                            e.message
                                ?: "Unable to connect to the server."

                    } finally {

                        isLoading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {

            Text(
                if (isLoading) {
                    "Saving..."
                } else {
                    "Save Period"
                }
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Back")
        }
    }
}