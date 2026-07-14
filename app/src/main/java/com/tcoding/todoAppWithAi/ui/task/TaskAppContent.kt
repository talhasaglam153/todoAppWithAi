package com.tcoding.todoAppWithAi.ui.task

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit
import com.tcoding.todoAppWithAi.data.TaskDatabase
import com.tcoding.todoAppWithAi.data.TaskRepository
import com.tcoding.todoAppWithAi.model.AppScreen
import com.tcoding.todoAppWithAi.model.NewTaskFormState
import com.tcoding.todoAppWithAi.model.TaskCategory
import com.tcoding.todoAppWithAi.model.TaskItem
import com.tcoding.todoAppWithAi.model.sampleTasks
import kotlinx.coroutines.launch

@Composable
fun TaskAppContent() {
    val context = LocalContext.current.applicationContext
    val repository = remember {
        TaskRepository(TaskDatabase.getInstance(context).taskDao())
    }
    val appPreferences = remember {
        context.getSharedPreferences("todo_app_preferences", Context.MODE_PRIVATE)
    }
    val scope = rememberCoroutineScope()

    var currentScreen by remember { mutableStateOf(AppScreen.TASK_LIST) }
    var selectedCategory by remember { mutableStateOf(TaskCategory.ALL) }
    var formState by remember { mutableStateOf(NewTaskFormState()) }
    var editingTaskId by remember { mutableStateOf<Long?>(null) }
    val tasks by repository.tasksFlow.collectAsState(initial = emptyList())

    LaunchedEffect(repository) {
        val isSeedCompleted = appPreferences.getBoolean(KEY_TASK_SEED_DONE, false)
        if (!isSeedCompleted) {
            repository.upsertTasks(sampleTasks)
            appPreferences.edit { putBoolean(KEY_TASK_SEED_DONE, true) }
        }
    }

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
                    scope.launch {
                        repository.toggleTask(taskId)
                    }
                },
                onTaskEdit = { taskId ->
                    val task = tasks.firstOrNull { it.id == taskId }
                    if (task != null) {
                        editingTaskId = taskId
                        formState = task.toFormState()
                        currentScreen = AppScreen.TASK_FORM
                    }
                },
                onTaskDelete = { taskId ->
                    scope.launch {
                        repository.deleteTask(taskId)
                    }
                },
                onAddTaskClick = {
                    editingTaskId = null
                    formState = NewTaskFormState()
                    currentScreen = AppScreen.TASK_FORM
                }
            )
        }

        AppScreen.TASK_FORM -> {
            NewTaskScreen(
                formState = formState,
                screenTitle = if (editingTaskId == null) "New Task" else "Edit Task",
                submitButtonText = if (editingTaskId == null) "Create Task" else "Save Changes",
                onBackClick = {
                    editingTaskId = null
                    formState = NewTaskFormState()
                    currentScreen = AppScreen.TASK_LIST
                },
                onTitleChange = { formState = formState.copy(title = it) },
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
                    val title = formState.title.trim().ifBlank { "New Task" }
                    val dueLabel = if (formState.dueTimeLabel == "Time") {
                        formState.dueDateLabel
                    } else {
                        "${formState.dueDateLabel}, ${formState.dueTimeLabel}"
                    }
                    val existingTask = editingTaskId?.let { taskId ->
                        tasks.firstOrNull { it.id == taskId }
                    }

                    val newTask = TaskItem(
                        id = editingTaskId ?: ((tasks.maxOfOrNull { it.id } ?: 0L) + 1L),
                        title = title,
                        description = formState.description.trim(),
                        dueLabel = dueLabel,
                        category = formState.category,
                        priority = formState.priority,
                        completed = existingTask?.completed ?: false
                    )

                    scope.launch {
                        repository.addTask(newTask)
                        selectedCategory = TaskCategory.ALL
                        editingTaskId = null
                        formState = NewTaskFormState()
                        currentScreen = AppScreen.TASK_LIST
                    }
                }
            )
        }
    }
}

private const val KEY_TASK_SEED_DONE = "task_seed_done"

private fun TaskItem.toFormState(): NewTaskFormState {
    val dateAndTime = dueLabel.split(", ", limit = 2)
    val dateLabel = dateAndTime.getOrNull(0).orEmpty().ifBlank { "Today" }
    val timeLabel = dateAndTime.getOrNull(1) ?: "Time"

    return NewTaskFormState(
        title = title,
        description = description,
        dueDateLabel = dateLabel,
        dueTimeLabel = timeLabel,
        category = category,
        priority = priority
    )
}
