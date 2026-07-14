package com.tcoding.todoAppWithAi.model

enum class TaskCategory(val label: String) {
    ALL("All"),
    WORK("Work"),
    PERSONAL("Personal"),
    URGENT("Urgent")
}

enum class TaskPriority {
    LOW,
    MEDIUM,
    HIGH
}

data class TaskItem(
    val id: Long,
    val title: String,
    val dueLabel: String,
    val category: TaskCategory,
    val priority: TaskPriority,
    val completed: Boolean = false
)
