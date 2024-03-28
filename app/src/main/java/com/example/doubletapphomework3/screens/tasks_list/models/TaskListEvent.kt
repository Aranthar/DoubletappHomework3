package com.example.doubletapphomework3.screens.tasks_list.models

import com.example.doubletapphomework3.data.Habit

sealed class TaskListEvent {
    data class RestoreHabits(val newHabit: Habit?) : TaskListEvent()
}
