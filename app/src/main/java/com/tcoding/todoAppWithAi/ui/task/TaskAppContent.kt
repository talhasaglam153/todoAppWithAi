package com.tcoding.todoAppWithAi.ui.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tcoding.todoAppWithAi.model.AppScreen
import com.tcoding.todoAppWithAi.model.NewTaskFormState
import com.tcoding.todoAppWithAi.model.TaskCategory
import com.tcoding.todoAppWithAi.model.TaskItem
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
                onTaskDelete = { taskId ->
                    tasks = tasks.filterNot { it.id == taskId }
                },
                onAddTaskClick = { currentScreen = AppScreen.TASK_FORM }
            )
        }

        AppScreen.TASK_FORM -> {
            NewTaskScreen(
                formState = formState,
                onBackClick = { currentScreen = AppScreen.TASK_LIST },
                onDescriptionChange = { formState = formState.copy(description = it) },
                onDateSelected = { formState = formState.copy(dueDateLabel = it) },
                onTimeSelected = { formState = formState.copy(dueTimeLabel = it) },
                onCategorySelected = {
                    formState = formState.copy(category = it)
                },
                onPrioritySelected = {
                    formState = formState.copy(priority = it)
                },
                onCreateClick = {
                    val title = formState.description.trim().ifBlank { "New Task" }
                    val dueLabel = if (formState.dueTimeLabel == "Time") {
                        formState.dueDateLabel
                    } else {
                        "${formState.dueDateLabel}, ${formState.dueTimeLabel}"
                    }

                    val newTask = TaskItem(
                        id = (tasks.maxOfOrNull { it.id } ?: 0L) + 1L,
                        title = title,
                        dueLabel = dueLabel,
                        category = formState.category,
                        priority = formState.priority
                    )

                    tasks = listOf(newTask) + tasks
                    selectedCategory = TaskCategory.ALL
                    formState = NewTaskFormState()
                    currentScreen = AppScreen.TASK_LIST
                }
            )
        }
    }
}
