package com.example.doubletapphomework3.screens.tasks_list

import com.example.doubletapphomework3.data.Habit
import com.example.doubletapphomework3.domain.BaseViewModel
import com.example.doubletapphomework3.screens.tasks_list.models.TaskListEvent
import com.example.doubletapphomework3.screens.tasks_list.models.TaskListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor() :
    BaseViewModel<TaskListViewState, TaskListEvent>(initialState = TaskListViewState.HabitsRestored()) {
    private val habits: ArrayList<Habit> = arrayListOf()

    override fun obtainEvent(viewEvent: TaskListEvent) {
        when (viewEvent) {
            is TaskListEvent.RestoreHabits -> {
                restoreHabits(viewEvent.newHabit)
            }
        }
    }

    private fun restoreHabits(newHabit: Habit?) {
        if (newHabit != null) {
            var isFind = false

            habits.forEachIndexed { index, habit ->
                if (habit.id == newHabit.id) {
                    habits[index] = habit
                    isFind = true
                    return@forEachIndexed
                }
            }

            if (!isFind) {
                habits.add(newHabit)
            }
        }

        viewState.update {
            TaskListViewState.HabitsRestored(habits)
        }
    }
}