package io.github.takusan23.littlehomedroid.widget

import android.content.Intent
import android.graphics.Bitmap

data class LittleHomeDroidWidgetData(
    val label: String,
    val icon: Bitmap,
    val intent: Intent
)