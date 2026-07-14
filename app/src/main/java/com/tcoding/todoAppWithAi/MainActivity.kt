package com.tcoding.todoAppWithAi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tcoding.todoAppWithAi.ui.task.TaskAppContent
import com.tcoding.todoAppWithAi.ui.theme.TodoAppWithAiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppWithAiTheme {
                TaskAppContent()
            }
        }
    }
}

@Composable
fun TodoPreview() {
    TodoAppWithAiTheme {
        TaskAppContent()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() = TodoPreview()
