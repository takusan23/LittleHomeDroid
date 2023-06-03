package io.github.takusan23.littlehomedroid.widget

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Bitmap
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import io.github.takusan23.littlehomedroid.usage.UsageStatusData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object LittleHomeDroidDataWidgetTool {

    /**
     * [UsageStatusData]の配列を、ウイジェット表示で使う[LittleHomeDroidWidgetData]に変換する
     *
     * @param context [Context]
     * @param list [io.github.takusan23.littlehomedroid.usage.UsageStatusTool.queryUsageAppDataList]
     */
    suspend fun convertWidgetData(context: Context, list: List<UsageStatusData>): List<LittleHomeDroidWidgetData> {
        val packageManager = context.packageManager
        val widgetData = list.map { usage ->
            val appInfo = getApplicationInfo(context, usage.packageName)
            LittleHomeDroidWidgetData(
                label = appInfo.loadLabel(packageManager).toString(),
                icon = createAppIconBitmap(context, appInfo.loadIcon(packageManager)),
                intent = packageManager.getLaunchIntentForPackage(appInfo.packageName)!!
            )
        }
        return widgetData
    }

    /**
     * アプリの情報を取得する
     *
     * @param context [Context]
     * @param applicationId パッケージ名
     * @return アプリ情報
     */
    private suspend fun getApplicationInfo(context: Context, applicationId: String) = withContext(Dispatchers.IO) {
        return@withContext if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getApplicationInfo(applicationId, PackageManager.ApplicationInfoFlags.of(0))
        } else {
            context.packageManager.getApplicationInfo(applicationId, 0)
        }
    }

    /**
     * アイコンの Bitmap を作成する
     *
     * @param context [Context]
     * @param drawable アイコンの[Drawable]。
     * @see [android.content.pm.ActivityInfo.loadIcon]
     * @return アイコン画像。テーマアイコンに対応していればテーマアイコンを返す
     */
    private fun createAppIconBitmap(context: Context, drawable: Drawable): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && drawable is AdaptiveIconDrawable && drawable.monochrome != null) {
            val (backgroundColor, foregroundColor) = getThemeIconColorPair(context)
            val monochromeIcon = drawable.monochrome!!.apply {
                mutate()
                setTint(foregroundColor)
            }
            AdaptiveIconDrawable(ColorDrawable(backgroundColor), monochromeIcon)
        } else {
            drawable
        }.toBitmap()
    }

    /**
     * テーマアイコンの時のバックグラウンド・フォアグラウンドの色を取得する
     * https://cs.android.com/android/platform/superproject/+/refs/heads/master:frameworks/libs/systemui/iconloaderlib/src/com/android/launcher3/icons/ThemedIconDrawable.java
     *
     * @param context [Context]
     * @return バックグラウンド・フォアグラウンドのPair
     */
    @RequiresApi(Build.VERSION_CODES.S)
    private fun getThemeIconColorPair(context: Context): Pair<Int, Int> {
        return if (context.resources.configuration.uiMode and UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES) {
            ContextCompat.getColor(context, android.R.color.system_neutral1_800) to ContextCompat.getColor(context, android.R.color.system_accent1_100)
        } else {
            ContextCompat.getColor(context, android.R.color.system_accent1_100) to ContextCompat.getColor(context, android.R.color.system_neutral2_700)
        }
    }
}