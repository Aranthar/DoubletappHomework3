package com.example.doubletapphomework3.screens.tasks_list.models

import com.example.doubletapphomework3.data.Habit

sealed class TaskListViewState {
    data class HabitsRestored(val habits: List<Habit> = listOf()) : TaskListViewState()
}