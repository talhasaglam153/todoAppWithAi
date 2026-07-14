package com.tcoding.todoAppWithAi.data

import com.tcoding.todoAppWithAi.model.TaskItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(
    private val taskDao: TaskDao
) {
    val tasksFlow: Flow<List<TaskItem>> = taskDao.observeTasks()
        .map { entities -> entities.map { it.toDomain() } }

    suspend fun upsertTasks(tasks: List<TaskItem>) {
        taskDao.upsertTasks(tasks.map { it.toEntity() })
    }

    suspend fun addTask(task: TaskItem) {
        taskDao.upsertTask(task.toEntity())
    }

    suspend fun toggleTask(taskId: Long) {
        taskDao.toggleTaskCompletion(taskId)
    }

    suspend fun deleteTask(taskId: Long) {
        taskDao.deleteTask(taskId)
    }
}
