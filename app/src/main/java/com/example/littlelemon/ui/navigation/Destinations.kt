package com.example.littlelemon.ui.navigation

interface Destinations {
    val route: String
    val title: String
}

object Home : Destinations {
    override val route: String = "Home"
    override val title: String = "Home"
}

object Onboarding : Destinations {
    override val route: String = "Onboarding"
    override val title: String = "Onboarding"
}

object Profile : Destinations {
    override val route: String = "Profile"
    override val title: String = "Profile"
}