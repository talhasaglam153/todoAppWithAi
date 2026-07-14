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
    var tasks by remember { mutableStateOf(sampleTasks) }

    val visibleTasks =
        if (selectedCategory == TaskCategory.ALL) {
            tasks
        } else {
            tasks.filter { it.category == selectedCategory }
        }

    when (currentScreen) {
        AppScreen.TASK_LIST -> {
            TaskListScreen(
                tasks = visibleTasks,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                onTaskClick = { taskId ->
                    tasks = tasks.map { task ->
                        if (task.id == taskId) task.copy(completed = !task.completed) else task
                    }
                },
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
