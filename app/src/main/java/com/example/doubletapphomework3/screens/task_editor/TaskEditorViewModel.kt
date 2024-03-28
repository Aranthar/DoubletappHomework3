package com.example.doubletapphomework3.screens.task_editor

import com.example.doubletapphomework3.data.Habit
import com.example.doubletapphomework3.domain.BaseViewModel
import com.example.doubletapphomework3.screens.task_editor.models.TaskEditorEvent
import com.example.doubletapphomework3.screens.task_editor.models.TaskEditorViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TaskEditorViewModel @Inject constructor() :
    BaseViewModel<TaskEditorViewState, TaskEditorEvent>(
        initialState = TaskEditorViewState.HabitRestored()
    ) {
    private var habit: Habit = Habit()

    override fun obtainEvent(viewEvent: TaskEditorEvent) {
        when (viewEvent) {
            is TaskEditorEvent.RestoreEdits -> {
                if (viewEvent.isEdit || viewEvent.habit != null && habit == Habit()) {
                    habit = viewEvent.habit!!
                }
            }

            is TaskEditorEvent.Init -> {
                viewState.update { TaskEditorViewState.HabitRestored() }
            }

            is TaskEditorEvent.ChangeColor -> habit.color = viewEvent.color
            is TaskEditorEvent.ChangeHabitType -> habit.type = viewEvent.type
            is TaskEditorEvent.ChangePriority -> habit.priority = viewEvent.priority
            is TaskEditorEvent.ChangeFieldText -> {
                when (viewEvent.type) {
                    TextFieldType.TITLE -> habit.title = viewEvent.text
                    TextFieldType.DESCRIPTION -> habit.description = viewEvent.text
                    TextFieldType.REPEAT_COUNT -> habit.executionCount = viewEvent.text
                    TextFieldType.PERIOD -> habit.period = viewEvent.text
                }
            }

            TaskEditorEvent.ClickBackBtn -> habit = Habit()
            TaskEditorEvent.ClickBtnSave -> {
                viewState.update { TaskEditorViewState.HabitSaved(habit) }
                habit = Habit()
            }
        }
    }
}