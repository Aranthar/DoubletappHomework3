package com.example.doubletapphomework3.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.doubletapphomework3.data.Habit
import com.example.doubletapphomework3.screens.task_editor.EditHabitScreen
import com.example.doubletapphomework3.screens.task_editor.TaskEditorViewModel
import com.example.doubletapphomework3.screens.tasks_list.TasksListScreen
import com.example.doubletapphomework3.screens.tasks_list.TasksListViewModel

class Navigation {
    private var navController: NavHostController? = null
    private var habit: Habit? = null
    private var isEdit = false

    @Composable
    fun Create() {
        navController = rememberNavController()
        val taskListViewModel = hiltViewModel<TasksListViewModel>()
        val taskEditorViewModel = hiltViewModel<TaskEditorViewModel>()

        NavHost(navController = navController!!, startDestination = TASKS_LIST_ROUTE) {
            composable(TASK_EDITOR_ROUTE) {
                CreateTaskEditorScreen(taskEditorViewModel, habit)
            }
            composable(TASKS_LIST_ROUTE) {
                CreateTasksListScreen(taskListViewModel, habit)
            }
        }
    }

    @Composable
    private fun CreateTasksListScreen(
        taskListViewModel: TasksListViewModel,
        habit: Habit?
    ) {
        TasksListScreen(
            viewModel = taskListViewModel,
            habit = habit,
            onCreateCard = {
                isEdit = false
                navController!!.navigate(TASK_EDITOR_ROUTE)
            },
            onHabitClick = {
                isEdit = true
                this.habit = it
                navController!!.navigate(TASK_EDITOR_ROUTE)
            }
        ).Create()
    }

    @Composable
    private fun CreateTaskEditorScreen(
        taskEditorViewModel: TaskEditorViewModel,
        habit: Habit?
    ) {
        EditHabitScreen(
            viewModel = taskEditorViewModel,
            habit = if (isEdit) habit else null,
            onSaveChange = {
                this.habit = it
                navController!!.navigate(TASKS_LIST_ROUTE)
            },
            onBackClick = {
                navController!!.popBackStack()
            }
        ).Create()
    }

    companion object {
        const val TASKS_LIST_ROUTE = "TASKS_LIST_ROUTE"
        const val TASK_EDITOR_ROUTE = "TASK_EDITOR_ROUTE"
    }
}