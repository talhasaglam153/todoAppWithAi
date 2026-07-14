package com.tcoding.todoAppWithAi.model

val sampleTasks = listOf(
    TaskItem(
        id = 1,
        title = "Review Compose UI",
        dueLabel = "Today, 10:00 AM",
        category = TaskCategory.WORK,
        priority = TaskPriority.HIGH
    ),
    TaskItem(
        id = 2,
        title = "Buy groceries for dinner",
        dueLabel = "Today, 6:30 PM",
        category = TaskCategory.PERSONAL,
        priority = TaskPriority.MEDIUM
    ),
    TaskItem(
        id = 3,
        title = "Pay electricity bill",
        dueLabel = "Tomorrow",
        category = TaskCategory.URGENT,
        priority = TaskPriority.HIGH
    ),
    TaskItem(
        id = 4,
        title = "Read 10 pages of book",
        dueLabel = "Yesterday",
        category = TaskCategory.PERSONAL,
        priority = TaskPriority.LOW,
        completed = true
    )
)
