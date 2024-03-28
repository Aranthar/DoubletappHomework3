package com.example.doubletapphomework3.screens.task_editor.models

import com.example.doubletapphomework3.data.Habit

sealed class TaskEditorViewState {
    data class HabitRestored(val habit: Habit? = null) : TaskEditorViewState()
    data class HabitSaved(val habit: Habit?) : TaskEditorViewState()
}