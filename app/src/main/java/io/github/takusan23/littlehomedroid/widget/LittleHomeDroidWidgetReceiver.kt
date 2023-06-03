package io.github.takusan23.littlehomedroid.widget

import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/** ウィジェットのブロードキャスト受け取りなど */
class LittleHomeDroidWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = LittleHomeDroidWidget()

    /** ボタンを押したらココに来る */
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        when (intent.getStringExtra(KEY_EVENT)?.let { BroadcastEvent.find(it) }) {
            BroadcastEvent.UPDATE -> {
                // ウイジェット更新
                val pendingResult = goAsync()
                MainScope().launch {
                    try {
                        LittleHomeDroidWidget().updateAll(context)
                    } finally {
                        pendingResult.finish()
                    }
                }
            }

            null -> {
                // do nothing
            }
        }
    }

    enum class BroadcastEvent(val action: String) {
        /** 更新 */
        UPDATE("update");

        companion object {
            fun find(action: String) = values().firstOrNull { it.action == action }
        }
    }

    companion object {

        private const val KEY_EVENT = "key_event"

        /**
         * ブロードキャストを送信する[Intent]を返す
         *
         * @param context [Context]
         */
        fun createBroadcastIntent(context: Context, broadcastEvent: BroadcastEvent): Intent {
            return Intent(context, LittleHomeDroidWidgetReceiver::class.java).apply {
                putExtra(KEY_EVENT, broadcastEvent.action)
            }
        }
    }
}