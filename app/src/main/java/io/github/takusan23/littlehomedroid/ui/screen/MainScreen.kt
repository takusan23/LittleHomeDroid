package io.github.takusan23.littlehomedroid.ui.screen

import androidx.compose.runtime.Composable
import io.github.takusan23.littlehomedroid.ui.theme.LittleHomeDroidTheme
import io.github.takusan23.littlehomedroid.ui.tool.SetStatusBarMaterial3

/** メイン画面 */
@Composable
fun MainScreen() {
    LittleHomeDroidTheme {
        SetStatusBarMaterial3()

        HomeScreen()
    }
}
