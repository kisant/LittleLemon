package com.example.littlelemon.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.navigation.Profile
import com.example.littlelemon.ui.components.TopAppBar
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

@Composable
fun Home(navController: NavHostController) {
    TopAppBar(isProfileIconVisible = true) { navController.navigate(Profile.route) }
}