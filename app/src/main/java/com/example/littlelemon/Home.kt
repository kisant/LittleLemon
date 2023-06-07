package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun Home(navController: NavHostController) {
    TopAppBar(isProfileIconVisible = true) { navController.navigate(Profile.route) }
}