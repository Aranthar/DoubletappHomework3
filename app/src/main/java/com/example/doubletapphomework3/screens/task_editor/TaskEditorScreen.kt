package com.example.doubletapphomework3.screens.task_editor

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.doubletapphomework3.R
import com.example.doubletapphomework3.data.Habit
import com.example.doubletapphomework3.data.HabitPriority
import com.example.doubletapphomework3.data.HabitType
import com.example.doubletapphomework3.screens.task_editor.models.TaskEditorEvent
import com.example.doubletapphomework3.screens.task_editor.models.TaskEditorViewState

class EditHabitScreen(
    private val viewModel: TaskEditorViewModel,
    private val habit: Habit? = null,
    private val onSaveChange: (Habit?) -> Unit,
    private val onBackClick: () -> Unit,
) {
    private var expanded = mutableStateOf(false)
    private var selectedOption = mutableStateOf(habit?.priority ?: HabitPriority.LOW)
    private var radioButtonState = mutableStateOf(
        if (habit != null) {
            habit.type == HabitType.GOOD
        } else true
    )

    @Composable
    fun Create() {
        val viewState by viewModel.getViewState().collectAsStateWithLifecycle()
        var title by remember { mutableStateOf(habit?.title ?: "") }
        var desc by remember { mutableStateOf(habit?.description ?: "") }
        var executionCount by remember { mutableStateOf(habit?.executionCount ?: "") }
        var period by remember { mutableStateOf(habit?.period ?: "") }

        Surface {
            Column(
                modifier = Modifier
                    .padding(vertical = 30.dp, horizontal = 20.dp)
                    .fillMaxSize()
            ) {
                TextField(
                    value = title,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        title = it
                        viewModel.obtainEvent(
                            TaskEditorEvent.ChangeFieldText(TextFieldType.TITLE, it)
                        )
                    },
                    label = { Text(text = "Название привычки") }
                )

                Spacer(modifier = Modifier.height(14.dp))

                TextField(
                    value = desc,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        desc = it
                        viewModel.obtainEvent(
                            TaskEditorEvent.ChangeFieldText(TextFieldType.DESCRIPTION, it)
                        )
                    },
                    label = { Text("Описание привычки") }
                )

                Spacer(modifier = Modifier.height(14.dp))

                DropdownMenu {
                    viewModel.obtainEvent(
                        TaskEditorEvent.ChangePriority(priority = selectedOption.value)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                HabitTypeRadioGroup {
                    viewModel.obtainEvent(viewEvent = TaskEditorEvent.ChangeHabitType(it))
                }

                Spacer(modifier = Modifier.height(14.dp))

                TextField(
                    value = executionCount,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        executionCount = it
                        viewModel.obtainEvent(
                            TaskEditorEvent.ChangeFieldText(TextFieldType.REPEAT_COUNT, it)
                        )
                    },
                    label = { Text("Количество выполнения") }
                )

                Spacer(modifier = Modifier.height(14.dp))

                TextField(
                    value = period,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        period = it
                        viewModel.obtainEvent(
                            TaskEditorEvent.ChangeFieldText(TextFieldType.PERIOD, it)
                        )
                    },
                    label = { Text("Периодичность выполнения") }
                )

                Spacer(modifier = Modifier.height(14.dp))

                ColorList()

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onBackClick) {
                        Text(text = "Назад")
                    }

                    TextButton(onClick = {
                        viewModel.obtainEvent(TaskEditorEvent.ClickBtnSave)
                    }) {
                        Text(text = "Сохранить изменения")
                    }
                }
            }
        }

        LaunchedEffect(key1 = Unit) {
            viewModel.obtainEvent(
                TaskEditorEvent.RestoreEdits(habit, habit != null)
            )
        }

        when (viewState) {
            is TaskEditorViewState.HabitRestored -> {

            }

            is TaskEditorViewState.HabitSaved -> {
                onSaveChange((viewState as TaskEditorViewState.HabitSaved).habit)

                viewModel.obtainEvent(
                    TaskEditorEvent.Init
                )
            }
        }
    }

    @Composable
    fun DropdownMenu(onChangeItem: () -> Unit) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.priority))

            Box {
                Button(onClick = { expanded.value = true }) {
                    Text(text = selectedOption.value.toString())
                }
            }

            androidx.compose.material3.DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                HabitPriority.entries.forEach { priority ->
                    DropdownMenuItem(
                        text = { Text(text = priority.name) },
                        onClick = {
                            selectedOption.value = priority
                            expanded.value = false
                            onChangeItem()
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun HabitTypeRadioGroup(setSelectedHabitType: (HabitType) -> Unit) {
        Column {
            Text(text = stringResource(R.string.habit_type))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = radioButtonState.value,
                    onClick = {
                        radioButtonState.value = true
                        setSelectedHabitType(HabitType.GOOD)
                    }
                )
                Text(text = HabitType.GOOD.toString())
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = !radioButtonState.value,
                    onClick = {
                        radioButtonState.value = false
                        setSelectedHabitType(HabitType.BAD)
                    }
                )
                Text(text = HabitType.BAD.toString())
            }

        }
    }

    @Composable
    fun ColorList() {
        val colors = listOf(Color.Red, Color.Blue, Color.Green)
        val selectedColor = remember { mutableStateOf(Color.White) }

        val background = Brush.horizontalGradient(
            colors = colors
        )

        Text(text = stringResource(R.string.select_color))

        Spacer(modifier = Modifier.height(7.dp))

        val scrollState = rememberScrollState()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .horizontalScroll(scrollState)
                .background(
                    brush = background
                )
                .padding(10.dp)
        ) {
            val count = 16
            repeat(count) { index ->
                ColoredCircle(Modifier.onGloballyPositioned {
                    val boundsInParent = it.boundsInParent()
                    val center = boundsInParent.center

                    Log.i("TEST", "${index}: ${it.boundsInParent()}")
                })
                if (index != count - 1) Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(selectedColor.value)
        )
    }

    @Composable
    fun ColoredCircle(modifier: Modifier) {
        Box(
            modifier = modifier
                .size(76.dp)
                .clip(CircleShape)
                .border(BorderStroke(1.dp, Color.White), shape = CircleShape)
                .background(Color.Transparent)
                .clickable {

                }
        )
    }
}

