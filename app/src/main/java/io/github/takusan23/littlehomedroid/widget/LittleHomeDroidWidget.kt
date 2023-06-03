package io.github.takusan23.littlehomedroid.widget

import android.content.Context
import android.os.Build
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.GridCells
import androidx.glance.appwidget.lazy.LazyVerticalGrid
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import io.github.takusan23.littlehomedroid.BanditMachine
import io.github.takusan23.littlehomedroid.usage.UsageStatusTool

/** ウィジェットのレイアウト */
class LittleHomeDroidWidget : GlanceAppWidget() {

    companion object {
        private val BackgroundColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) android.R.color.system_accent2_100 else android.R.color.white
        private val BackgroundInnerColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) android.R.color.system_accent2_900 else android.R.color.darker_gray
        private val WidgetContent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) android.R.color.system_accent1_900 else android.R.color.darker_gray
        private val WidgetInnerContent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) android.R.color.system_accent2_100 else android.R.color.white
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val usageStatusDataList = UsageStatusTool.queryUsageAppDataList(context)
        val banditResult = BanditMachine.playAndResultAppList(usageStatusDataList, 10)
        val widgetDataList = LittleHomeDroidDataWidgetTool.convertWidgetData(context, banditResult)

        // Glance 専用の関数が必要
        provideContent {
            Column(
                modifier = GlanceModifier
                    .background(BackgroundColor)
                    .cornerRadius(16.dp)
            ) {
                Text(
                    modifier = GlanceModifier.padding(5.dp),
                    text = "LittleHomeDroid",
                    style = TextStyle(color = ColorProvider(WidgetContent), fontSize = 18.sp)
                )

                Box(modifier = GlanceModifier.padding(3.dp)) {
                    LazyVerticalGrid(
                        modifier = GlanceModifier
                            .background(BackgroundInnerColor)
                            .cornerRadius(8.dp),
                        gridCells = GridCells.Fixed(5)
                    ) {

                        items(widgetDataList) { (label, icon, intent) ->
                            Column(
                                modifier = GlanceModifier
                                    .padding(5.dp)
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.Vertical.CenterVertically,
                                horizontalAlignment = Alignment.Horizontal.CenterHorizontally
                            ) {
                                // なぜか Column に clickable をセットできなかったのでアイコンとテキストにセットしている
                                Image(
                                    modifier = GlanceModifier
                                        .size(40.dp)
                                        .clickable(actionStartActivity(intent)),
                                    provider = ImageProvider(icon), contentDescription = null
                                )
                                Text(
                                    modifier = GlanceModifier
                                        .clickable(actionStartActivity(intent)),
                                    text = label,
                                    style = TextStyle(color = ColorProvider(WidgetInnerContent), fontSize = 12.sp),
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}