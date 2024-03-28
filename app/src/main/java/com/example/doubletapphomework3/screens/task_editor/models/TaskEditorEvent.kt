package com.example.doubletapphomework3.screens.task_editor.models

import androidx.compose.ui.graphics.Color
import com.example.doubletapphomework3.data.Habit
import com.example.doubletapphomework3.data.HabitPriority
import com.example.doubletapphomework3.data.HabitType
import com.example.doubletapphomework3.screens.task_editor.TextFieldType

sealed class TaskEditorEvent {
    data class RestoreEdits(val habit: Habit?, val isEdit: Boolean) : TaskEditorEvent()
    data object Init : TaskEditorEvent()

    data class ChangeColor(val color: Color) : TaskEditorEvent()
    data class ChangePriority(val priority: HabitPriority) : TaskEditorEvent()
    data class ChangeFieldText(val type: TextFieldType, val text: String) : TaskEditorEvent()
    data class ChangeHabitType(val type: HabitType) : TaskEditorEvent()

    data object ClickBtnSave : TaskEditorEvent()
    data object ClickBackBtn : TaskEditorEvent()
}