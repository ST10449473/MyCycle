package com.example.mycycle.home

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onSettings: () -> Unit,
    onTrackPeriod: () -> Unit,
    onCalendar: () -> Unit,
    onJournal: () -> Unit,
    onAchievements: () -> Unit,
    onEducation: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Welcome to MyCycle",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = onTrackPeriod,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Track My Period")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            onClick = onCalendar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cycle Calendar")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            onClick = onJournal,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Symptoms & Mood")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            onClick = onAchievements,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Achievements")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            onClick = onEducation,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Health Education")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            onClick = onSettings,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Settings")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}