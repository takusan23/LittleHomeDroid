package io.github.takusan23.littlehomedroid.usage

/**
 * アプリの使用状況のデータクラス
 *
 * @param packageName パッケージ名
 * @param foregroundUsageTimeMs フォアグラウンド状態で利用していた期間
 */
data class UsageStatusData(
    val packageName: String,
    val foregroundUsageTimeMs: Long
)