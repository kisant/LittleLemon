package com.example.littlelemon.ui.navigation

import android.content.Context.MODE_PRIVATE
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.util.Constants.PREFS_USER_DATA
import com.example.littlelemon.util.Constants.USER_NAME_KEY
import com.example.littlelemon.ui.home.Home
import com.example.littlelemon.ui.onboarding.Onboarding
import com.example.littlelemon.ui.profile.Profile

@Composable
fun Navigation(navController: NavHostController) {
    val context = LocalContext.current
    val userName by lazy {
        context.getSharedPreferences(PREFS_USER_DATA, MODE_PRIVATE).run {
            getString(USER_NAME_KEY, null)
        }
    }
    val startDestination = if (userName != null) Home.route else Onboarding.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) {
            Onboarding(navController)
        }
        composable(Home.route) {
            Home(navController)
        }
        composable(Profile.route) {
            Profile(navController)
        }
    }
}