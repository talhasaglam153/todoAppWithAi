package com.tcoding.todoAppWithAi.ui.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tcoding.todoAppWithAi.model.AppScreen
import com.tcoding.todoAppWithAi.model.NewTaskFormState
import com.tcoding.todoAppWithAi.model.TaskCategory
import com.tcoding.todoAppWithAi.model.sampleTasks

@Composable
fun TaskAppContent() {
    var currentScreen by remember { mutableStateOf(AppScreen.TASK_LIST) }
    var selectedCategory by remember { mutableStateOf(TaskCategory.ALL) }
    var formState by remember { mutableStateOf(NewTaskFormState()) }

    val visibleTasks = remember(selectedCategory) {
        if (selectedCategory == TaskCategory.ALL) {
            sampleTasks
        } else {
            sampleTasks.filter { it.category == selectedCategory }
        }
    }

    when (currentScreen) {
        AppScreen.TASK_LIST -> {
            TaskListScreen(
                tasks = visibleTasks,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                onAddTaskClick = { currentScreen = AppScreen.TASK_FORM }
            )
        }

        AppScreen.TASK_FORM -> {
            NewTaskScreen(
                formState = formState,
                onBackClick = { currentScreen = AppScreen.TASK_LIST },
                onCategorySelected = {
                    formState = formState.copy(category = it)
                },
                onPrioritySelected = {
                    formState = formState.copy(priority = it)
                },
                onCreateClick = { currentScreen = AppScreen.TASK_LIST }
            )
        }
    }
}
