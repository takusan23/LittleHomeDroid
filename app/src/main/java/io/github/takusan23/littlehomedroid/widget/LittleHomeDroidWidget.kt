package io.github.takusan23.littlehomedroid.widget

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.lazy.GridCells
import androidx.glance.appwidget.lazy.LazyVerticalGrid
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import io.github.takusan23.littlehomedroid.R

/** ウィジェットのレイアウト */
class LittleHomeDroidWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        // データ取得など...

        // Glance 専用の関数が必要
        provideContent {
            Column(modifier = GlanceModifier.background(Color.White)) {
                Text(
                    modifier = GlanceModifier.padding(5.dp),
                    text = "LittleHomeDroid"
                )
                LazyVerticalGrid(gridCells = GridCells.Fixed(5)) {
                    items(20) {
                        Column(
                            modifier = GlanceModifier.fillMaxSize(),
                            verticalAlignment = Alignment.Vertical.CenterVertically,
                            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
                        ) {
                            Image(provider = ImageProvider(R.drawable.ic_launcher_foreground), contentDescription = null)
                            Text(text = "App name")
                        }
                    }
                }
            }
        }
    }
}