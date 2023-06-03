package io.github.takusan23.littlehomedroid.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import io.github.takusan23.littlehomedroid.BanditMachine
import io.github.takusan23.littlehomedroid.usage.UsageStatusData
import io.github.takusan23.littlehomedroid.usage.UsageStatusTool

/** 最初の画面 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val usageDataList = remember { mutableStateOf(emptyList<UsageStatusData>()) }

    LaunchedEffect(key1 = Unit) {
        // usageDataList.value = UsageStatsTool.queryUsageAppDataList(context)
        usageDataList.value = BanditMachine.playAndResultAppList(UsageStatusTool.queryUsageAppDataList(context), 10)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = "リスト") },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(usageDataList.value) { data ->
                    Text(text = "${data.packageName} = ${data.foregroundUsageTimeMs / 1000} 秒利用")
                    Divider()
                }
            }
        }
    }
}