package com.qcuncle.teenstudy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qcuncle.teenstudy.config.Extra
import com.qcuncle.teenstudy.service.HtmlImpl
import com.qcuncle.teenstudy.ui.theme.TeenStudyTheme
import com.qcuncle.teenstudy.util.HtmlUtil
import com.qcuncle.teenstudy.util.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    private val startStr = "http"
    private val endStr = "m.html"
    private val imgStr = "images/end.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeenStudyTheme {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "请将微信公众号中青年大学习地址粘贴到下方↓",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth().padding(36.dp, 16.dp)
                    )

                    val website = remember { mutableStateOf("") }
                    TextField(
                        placeholder = { Text(text = "请输入~", fontSize = 14.sp) },
                        value = website.value,
                        maxLines = 2,
                        onValueChange = {
                            website.value = it
                        },
                        modifier = Modifier.fillMaxWidth().padding(36.dp, 16.dp)
                    )
                    Button(
                        onClick = { onBtnClick(website.value) },
                        modifier = Modifier.padding(36.dp), shape = CircleShape
                    ) {
                        Text(text = "进入进入~", modifier = Modifier.padding(4.dp))
                    }
                }
            }
        }
    }

    /**
     * 点击进入学习
     */
    private fun onBtnClick(url: String?) {
        if (url.isNullOrBlank()) {
            this@HomeActivity.showToast("请先输入学习地址")
        } else if (url.startsWith(startStr) && url.endsWith(endStr)) {
            // 图片地址
            val imgUrl = url.replace(endStr, imgStr)
            CoroutineScope(Dispatchers.Main).launch {
                // 获取网站的html内容
                val htmlBody: String? = HtmlImpl().getHtmlBody(url)
                // 获取网站标题
                val result: String = if (htmlBody.isNullOrEmpty()) {
                    this@HomeActivity.showToast("获取青年大学习标题失败")
                    ""
                } else {
                    htmlBody.replace("\r\n|\r|\n".toRegex(), "")
                }
                val title = HtmlUtil.getTitle(result)
                jumpToScreenshot(title, imgUrl)
            }
        } else {
            this@HomeActivity.showToast("请检查地址是否输入正确")
        }
    }

    /**
     * 跳转到学习截图页面
     */
    private fun jumpToScreenshot(title: String, imgUrl: String) {
        startActivity(Intent(this, ScreenshotActivity::class.java).apply {
            putExtra(Extra.STUDY_TITLE, title)
            putExtra(Extra.STUDY_SHOT, imgUrl)
        })
    }
}