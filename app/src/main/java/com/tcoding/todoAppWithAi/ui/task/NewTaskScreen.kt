package com.tcoding.todoAppWithAi.ui.task

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Composable
fun NewTaskScreen(
    formState: NewTaskFormState,
    onBackClick: () -> Unit,
    onCategorySelected: (TaskCategory) -> Unit,
    onPrioritySelected: (TaskPriority) -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(SurfaceWhite)
                .border(width = 1.dp, color = BorderSoft, shape = RoundedCornerShape(18.dp))
                .padding(16.dp)
        ) {
            Text(
                text = if (formState.description.isBlank()) "Add details, links, etc..." else formState.description,
                color = TextSecondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            DateTimeBox(text = "Today", modifier = Modifier.weight(1f))
            DateTimeBox(text = "Time", modifier = Modifier.weight(1f))
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
private fun DateTimeBox(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .border(width = 1.dp, color = BorderSoft, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        )
    }
}
