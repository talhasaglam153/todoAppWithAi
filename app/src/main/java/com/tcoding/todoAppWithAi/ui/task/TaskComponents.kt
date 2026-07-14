package com.tcoding.todoAppWithAi.ui.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tcoding.todoAppWithAi.model.TaskCategory
import com.tcoding.todoAppWithAi.model.TaskItem
import com.tcoding.todoAppWithAi.model.TaskPriority
import com.tcoding.todoAppWithAi.ui.theme.BorderSoft
import com.tcoding.todoAppWithAi.ui.theme.PrimaryBlue
import com.tcoding.todoAppWithAi.ui.theme.PriorityHigh
import com.tcoding.todoAppWithAi.ui.theme.PriorityLow
import com.tcoding.todoAppWithAi.ui.theme.PriorityMedium
import com.tcoding.todoAppWithAi.ui.theme.SurfaceWhite
import com.tcoding.todoAppWithAi.ui.theme.TextPrimary
import com.tcoding.todoAppWithAi.ui.theme.TextSecondary

@Composable
fun TaskCategoryChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) PrimaryBlue else SurfaceWhite
    val textColor = if (isSelected) SurfaceWhite else TextPrimary

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(backgroundColor)
            .border(width = 1.dp, color = BorderSoft, shape = RoundedCornerShape(999.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun TaskCard(
    task: TaskItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(SurfaceWhite)
            .border(width = 1.dp, color = BorderSoft, shape = RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .alpha(if (task.completed) 0.55f else 1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkColor = if (task.completed) PrimaryBlue else BorderSoft

        Box(
            modifier = Modifier
                .size(24.dp)
                .border(width = 2.dp, color = checkColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (task.completed) {
                Text(text = "✓", color = PrimaryBlue, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = task.title,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = task.dueLabel,
                color = TextSecondary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
        PriorityDot(priority = task.priority)
    }
}

@Composable
fun PriorityDot(priority: TaskPriority) {
    val color = when (priority) {
        TaskPriority.HIGH -> PriorityHigh
        TaskPriority.MEDIUM -> PriorityMedium
        TaskPriority.LOW -> PriorityLow
    }

    Box(
        modifier = Modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun PrioritySelector(
    selectedPriority: TaskPriority,
    onPrioritySelected: (TaskPriority) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(SurfaceWhite)
            .border(width = 1.dp, color = BorderSoft, shape = RoundedCornerShape(18.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TaskPriority.entries.forEach { priority ->
            val selected = priority == selectedPriority
            val itemBackground = if (selected) SurfaceWhite else Color.Transparent
            val textColor = if (selected) TextPrimary else TextSecondary

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(itemBackground)
                    .border(
                        width = if (selected) 1.dp else 0.dp,
                        color = BorderSoft,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable { onPrioritySelected(priority) }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = priority.name.lowercase().replaceFirstChar { it.uppercase() },
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

fun categoryLabel(category: TaskCategory): String {
    return category.label
}
