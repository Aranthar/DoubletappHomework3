package com.example.doubletapphomework3.screens.tasks_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.doubletapphomework3.R
import com.example.doubletapphomework3.components.HabitCard
import com.example.doubletapphomework3.data.Habit
import com.example.doubletapphomework3.screens.tasks_list.models.TaskListEvent
import com.example.doubletapphomework3.screens.tasks_list.models.TaskListViewState

class TasksListScreen(
    private val viewModel: TasksListViewModel,
    private val habit: Habit?,
    private val onCreateCard: () -> Unit,
    private val onHabitClick: (Habit) -> Unit,
) {
    private var habits = mutableStateOf(listOf<Habit>())

    @Composable
    fun Create() {
        val viewState by viewModel.getViewState().collectAsStateWithLifecycle()

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    onCreateCard()
                }) {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add))
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            content = { padding ->
                HabitList(habits = habits.value, padding = padding, onHabitClick)
            }
        )

        LaunchedEffect(key1 = Unit) {
            viewModel.obtainEvent(TaskListEvent.RestoreHabits(habit))
        }

        when (viewState) {
            is TaskListViewState.HabitsRestored -> {
                habits.value = (viewState as TaskListViewState.HabitsRestored).habits
            }
        }
    }

    @Composable
    private fun HabitList(
        habits: List<Habit>,
        padding: PaddingValues,
        onHabitClick: (Habit) -> Unit,
    ) {
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
        ) {
            items(habits.size) { index ->
                HabitCard(habitCard = habits[index], onHabitClick = onHabitClick)
                if (index != habits.size - 1) Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}