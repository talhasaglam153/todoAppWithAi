package com.tcoding.todoAppWithAi.model

enum class AppScreen {
    TASK_LIST,
    TASK_FORM
}

data class NewTaskFormState(
    val title: String = "",
    val description: String = "",
    val dueDateLabel: String = "Today",
    val dueTimeLabel: String = "Time",
    val category: TaskCategory = TaskCategory.WORK,
    val priority: TaskPriority = TaskPriority.MEDIUM
)
