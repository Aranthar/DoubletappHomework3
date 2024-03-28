package com.example.doubletapphomework3.data

import androidx.compose.ui.graphics.Color
import java.io.Serializable
import java.util.UUID

data class Habit(
    var id : UUID = UUID.randomUUID(),
    var title: String = "",
    var description: String = "",
    var priority: HabitPriority = HabitPriority.LOW,
    var type: HabitType = HabitType.GOOD,
    var executionCount: String = "",
    var period: String = "",
    var color: Color = Color.Red
): Serializable