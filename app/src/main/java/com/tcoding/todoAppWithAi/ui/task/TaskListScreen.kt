package com.tcoding.todoAppWithAi.ui.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tcoding.todoAppWithAi.model.TaskCategory
import com.tcoding.todoAppWithAi.model.TaskItem
import com.tcoding.todoAppWithAi.ui.theme.BackgroundGray
import com.tcoding.todoAppWithAi.ui.theme.BorderSoft
import com.tcoding.todoAppWithAi.ui.theme.PrimaryBlue
import com.tcoding.todoAppWithAi.ui.theme.SurfaceWhite
import com.tcoding.todoAppWithAi.ui.theme.TextPrimary
import com.tcoding.todoAppWithAi.ui.theme.TextSecondary

@Composable
fun TaskListScreen(
    tasks: List<TaskItem>,
    selectedCategory: TaskCategory,
    onCategorySelected: (TaskCategory) -> Unit,
    onAddTaskClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BackgroundGray,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick,
                containerColor = PrimaryBlue,
                shape = RoundedCornerShape(20.dp),
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "+",
                    color = SurfaceWhite,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TaskListHeader(
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "TODAY'S TASKS",
                color = TextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 100.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tasks.size) { index ->
                    TaskCard(task = tasks[index])
                }
            }
        }
    }
}

@Composable
private fun TaskListHeader(
    selectedCategory: TaskCategory,
    onCategorySelected: (TaskCategory) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceWhite, RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
            .padding(horizontal = 20.dp, vertical = 18.dp)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Good Morning, 👋",
                    color = TextSecondary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "My Tasks",
                    color = TextPrimary,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(BackgroundGray)
                    .border(width = 1.dp, color = BorderSoft, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "⌕",
                    color = TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TaskCategory.entries.forEach { category ->
                TaskCategoryChip(
                    label = categoryLabel(category),
                    isSelected = category == selectedCategory,
                    onClick = { onCategorySelected(category) }
                )
            }
        }
    }
}
