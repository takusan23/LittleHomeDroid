package io.github.takusan23.littlehomedroid.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/** ウィジェットのブロードキャスト受け取りなど */
class LittleHomeDroidWidgetReceiver:GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = LittleHomeDroidWidget()

}