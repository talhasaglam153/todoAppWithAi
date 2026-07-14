package com.tcoding.todoAppWithAi.ui.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tcoding.todoAppWithAi.model.NewTaskFormState
import com.tcoding.todoAppWithAi.model.TaskCategory
import com.tcoding.todoAppWithAi.model.TaskPriority
import com.tcoding.todoAppWithAi.ui.theme.BackgroundGray
import com.tcoding.todoAppWithAi.ui.theme.BorderSoft
import com.tcoding.todoAppWithAi.ui.theme.PrimaryBlue
import com.tcoding.todoAppWithAi.ui.theme.SurfaceWhite
import com.tcoding.todoAppWithAi.ui.theme.TextPrimary
import com.tcoding.todoAppWithAi.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun NewTaskScreen(
    formState: NewTaskFormState,
    onBackClick: () -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDateSelected: (String) -> Unit,
    onTimeSelected: (String) -> Unit,
    onCategorySelected: (TaskCategory) -> Unit,
    onPrioritySelected: (TaskPriority) -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onBackClick)
                    .padding(end = 18.dp)
            )
            Text(
                text = "New Task",
                color = TextPrimary,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 36.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        SectionLabel(text = "Description (Optional)")
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = formState.description,
            onValueChange = onDescriptionChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 140.dp),
            textStyle = TextStyle(
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            ),
            placeholder = {
                Text(
                    text = "Add details, links, etc...",
                    color = TextSecondary,
                    fontSize = 20.sp
                )
            },
            shape = RoundedCornerShape(18.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceWhite,
                unfocusedContainerColor = SurfaceWhite,
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = BorderSoft,
                cursorColor = PrimaryBlue
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            DateTimeBox(
                text = formState.dueDateLabel,
                modifier = Modifier.weight(1f),
                onClick = {
                    val now = Calendar.getInstance()
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val selectedDate = Calendar.getInstance().apply {
                                set(year, month, dayOfMonth)
                            }
                            val today = Calendar.getInstance()
                            val isToday =
                                selectedDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                                    selectedDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)

                            val label = if (isToday) {
                                "Today"
                            } else {
                                SimpleDateFormat("dd MMM", Locale.getDefault()).format(selectedDate.time)
                            }
                            onDateSelected(label)
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            )
            DateTimeBox(
                text = formState.dueTimeLabel,
                modifier = Modifier.weight(1f),
                onClick = {
                    val now = Calendar.getInstance()
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            onTimeSelected(
                                String.format(
                                    Locale.getDefault(),
                                    "%02d:%02d",
                                    hourOfDay,
                                    minute
                                )
                            )
                        },
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                    ).show()
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        SectionLabel(text = "Category")
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TaskCategory.entries.filter { it != TaskCategory.ALL }.forEach { category ->
                TaskCategoryChip(
                    label = categoryLabel(category),
                    isSelected = category == formState.category,
                    onClick = { onCategorySelected(category) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        SectionLabel(text = "Priority")
        Spacer(modifier = Modifier.height(10.dp))
        PrioritySelector(
            selectedPriority = formState.priority,
            onPrioritySelected = onPrioritySelected
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onCreateClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue,
                contentColor = SurfaceWhite
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
        ) {
            Text(
                text = "Create Task",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = TextPrimary,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    )
}

@Composable
private fun DateTimeBox(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .border(width = 1.dp, color = BorderSoft, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Text(text = "▾", color = TextSecondary, fontSize = 14.sp)
        }
    }
}
