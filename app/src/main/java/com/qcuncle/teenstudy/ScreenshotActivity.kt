package com.qcuncle.teenstudy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.qcuncle.teenstudy.config.Extra
import com.qcuncle.teenstudy.ui.theme.TeenStudyTheme
import com.qcuncle.teenstudy.ui.widget.ImmersionStatusBar
import com.qcuncle.teenstudy.ui.widget.StudyScreenshot
import com.qcuncle.teenstudy.ui.widget.TeenStudyToolBar
import com.qcuncle.teenstudy.util.showLongToast

class ScreenshotActivity : ComponentActivity() {

    private lateinit var title: String
    private lateinit var imgUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        title = intent.extras?.getString(Extra.STUDY_TITLE) ?: "青年大学习"
        imgUrl = intent.extras?.getString(Extra.STUDY_SHOT) ?: ""
        setContent {
            TeenStudyTheme {
                // 在主题内设置自定义状态栏
                ImmersionStatusBar(background = Color(237, 237, 237)) {
                    val isShow = remember { mutableStateOf(false) }
                    Column {
                        TeenStudyToolBar(
                            title = title,
                            leftAction = { finish() },
                            rightAction = {
                                this@ScreenshotActivity.showLongToast("如果说我们还有什么丝毫的关系话，那大概就是我们都是社会的接班人罢了")
                                isShow.value = true
                            })
                        StudyScreenshot(imgUrl)
                    }
                    InputDialog(isShow)
                }
            }
        }
    }

    @Composable
    fun InputDialog(showDialog: MutableState<Boolean>) {
        Column {
            val projectUrl = "https://github.com/QCuncle/TeenStudy"
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog.value = false
                    },
                    title = {
                        Text(text = "About")
                    },
                    text = {
                        Text("A screenshot app for TeenStudy created by github@QCuncle using ComposeUI\n\n$projectUrl")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog.value = false
                                startActivity(
                                    Intent("android.intent.action.VIEW", Uri.parse(projectUrl))
                                )
                            }
                        ) {
                            Text("Go")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialog.value = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}