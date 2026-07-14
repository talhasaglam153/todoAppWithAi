package com.tcoding.todoAppWithAi.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tcoding.todoAppWithAi.model.TaskCategory
import com.tcoding.todoAppWithAi.model.TaskItem
import com.tcoding.todoAppWithAi.model.TaskPriority

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String,
    val dueLabel: String,
    val category: String,
    val priority: String,
    val completed: Boolean
)

fun TaskEntity.toDomain(): TaskItem {
    return TaskItem(
        id = id,
        title = title,
        description = description,
        dueLabel = dueLabel,
        category = enumValueOrDefault(category, TaskCategory.WORK),
        priority = enumValueOrDefault(priority, TaskPriority.MEDIUM),
        completed = completed
    )
}

fun TaskItem.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        dueLabel = dueLabel,
        category = category.name,
        priority = priority.name,
        completed = completed
    )
}

private inline fun <reified T : Enum<T>> enumValueOrDefault(value: String, fallback: T): T {
    return enumValues<T>().firstOrNull { it.name == value } ?: fallback
}
