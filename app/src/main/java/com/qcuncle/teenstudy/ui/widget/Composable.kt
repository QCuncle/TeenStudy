package com.qcuncle.teenstudy.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.qcuncle.teenstudy.R

/**
 * 青年大学习截图页面标题栏 [title]为标题文字 [leftAction]左侧按钮点击事件 [rightAction] 右侧按钮点击事件
 */
@Composable
fun TeenStudyToolBar(title: String, leftAction: () -> Unit, rightAction: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_back_bg),
            contentDescription = "关闭",

            modifier = Modifier
                .padding(8.dp, 12.dp)
                .size(25.dp)
                .align(Alignment.CenterStart)
                .clickable(onClick = { leftAction.invoke() },
                    indication = null, interactionSource = remember { MutableInteractionSource() })
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 17.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .align(Alignment.Center)
        )
        Image(
            painter = painterResource(R.drawable.ic_edit_bg),
            contentDescription = "更多",
            modifier = Modifier
                .padding(16.dp, 12.dp)
                .size(25.dp)
                .align(Alignment.CenterEnd)
                .clickable(onClick = { rightAction.invoke() },
                    indication = null, interactionSource = remember { MutableInteractionSource() })
        )
    }
}

/**
 *青年大学习截图[url]为截图地址
 */
@Composable
fun StudyScreenshot(url: String) {
    AsyncImage(
        model = url,
        contentDescription = "青年大学习截图",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds,
        alignment = Alignment.Center
    )
}

/**
 * 自定义系统状态栏
 * 使用该方法进行沉浸,还需在 Activity 中设置 WindowCompat.setDecorFitsSystemWindows(window, false)
 * 沉浸状态栏设置
 * @param color Color 状态栏颜色
 * @param darkIcons Boolean 是否是深色模式
 * @param content [@androidx.compose.runtime.Composable] Function0<Unit> 布局内容
 */
@Composable
fun ImmersionStatusBar(
    color: Color = Color.Transparent,
    darkIcons: Boolean = MaterialTheme.colors.isLight,
    background: Color = MaterialTheme.colors.background,
    content: @Composable () -> Unit,
) {
    ProvideWindowInsets {
        rememberSystemUiController().run {
            // 设置状态栏颜色
            setStatusBarColor(
                color = color,
                darkIcons = darkIcons
            )
            // 将状态栏和导航栏设置为color
            setSystemBarsColor(color = color, darkIcons = darkIcons)
            // 设置导航栏颜色
            setNavigationBarColor(color = color, darkIcons = darkIcons)
        }
        Column(
            Modifier
                .background(background)
                .fillMaxSize(),
        ) {
            // 因为系统状态栏被隐藏掉,需要创建一个自定义头部,高度为系统栏高度
            Spacer(
                modifier = Modifier
                    .statusBarsHeight()
                    .fillMaxWidth()
            )
            content()
        }
    }
}