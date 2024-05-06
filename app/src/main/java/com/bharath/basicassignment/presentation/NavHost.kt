package com.bharath.basicassignment.presentation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bharath.basicassignment.presentation.home.HomeScreen
import com.bharath.basicassignment.presentation.playvideo.VideoPlayerScreen

sealed class Screens(val route: String) {
    data object Home : Screens("home")
    data object VideoPlayer : Screens("videoPlayer")
}


@Composable
fun MyNavHost(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            HomeScreen(navHostController = navHostController)
        }
        composable(Screens.VideoPlayer.route + "/{videoId}", enterTransition = {
            fadeIn() + slideIntoContainer(Up)
        }, exitTransition = {
            fadeOut()+slideOutOfContainer(Down)
        }) {
            VideoPlayerScreen(navHostController = navHostController)
        }

    }
}