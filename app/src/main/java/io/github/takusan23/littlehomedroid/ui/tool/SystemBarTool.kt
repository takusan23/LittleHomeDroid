package io.github.takusan23.littlehomedroid.ui.tool

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

@Composable
fun SetStatusBarMaterial3() {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        WindowCompat.setDecorFitsSystemWindows((context as Activity).window, false)
    }
}