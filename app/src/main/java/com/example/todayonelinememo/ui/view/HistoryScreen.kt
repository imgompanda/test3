package com.example.todayonelinememo.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todayonelinememo.ui.theme.TodayOneLineMemoTheme

// Placeholder data structure
data class MemoItem(val id: Int, val date: String, val text: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    // Later, we'll add a parameter for the list of memos
) {
    // Placeholder memo list
    val memos = listOf(
        MemoItem(1, "2023-10-26", "오늘은 Compose UI를 만들었다. 꽤 귀엽게 만들어진 것 같다!"),
        MemoItem(2, "2023-10-25", "어제는 날씨가 좋아서 산책을 나갔다. 파스텔톤 하늘이 예뻤다."),
        MemoItem(3, "2023-10-24", "MVVM 아키텍처와 Clean Architecture에 대해 공부했다. 어렵지만 재미있다.")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("메모 기록") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    // Match the MainScreen AppBar color
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply Scaffold padding
                .padding(horizontal = 16.dp), // Add horizontal padding for the list
            contentPadding = PaddingValues(vertical = 16.dp), // Add vertical padding for the list content
            verticalArrangement = Arrangement.spacedBy(12.dp) // Spacing between cards
        ) {
            items(memos) { memo ->
                MemoHistoryItem(memo)
            }
        }
    }
}

@Composable
fun MemoHistoryItem(memo: MemoItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium, // Apply rounded corners
        colors = CardDefaults.cardColors(
            // Use surfaceVariant for a subtle background color
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Remove elevation for a flatter look
    ) {
        Column(modifier = Modifier.padding(16.dp)) { // Consistent padding
            Text(
                text = memo.date,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant // Ensure text color contrasts
            )
            Spacer(modifier = Modifier.height(8.dp)) // Increased spacing
            Text(
                text = memo.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface // Ensure text color contrasts
            )
        }
    }
}

@Preview(showBackground = true, name = "History Screen Light")
@Composable
fun HistoryScreenPreviewLight() {
    TodayOneLineMemoTheme(darkTheme = false) {
        HistoryScreen(rememberNavController())
    }
}

@Preview(showBackground = true, name = "History Screen Dark")
@Composable
fun HistoryScreenPreviewDark() {
    TodayOneLineMemoTheme(darkTheme = true) {
        HistoryScreen(rememberNavController())
    }
}


@Preview(showBackground = true, name = "History Item Light")
@Composable
fun MemoHistoryItemPreviewLight() {
    TodayOneLineMemoTheme(darkTheme = false) {
        MemoHistoryItem(MemoItem(1, "2023-10-26", "미리보기용 메모입니다. 밝은 테마입니다."))
    }
}
@Preview(showBackground = true, name = "History Item Dark")
@Composable
fun MemoHistoryItemPreviewDark() {
    TodayOneLineMemoTheme(darkTheme = true) {
        MemoHistoryItem(MemoItem(1, "2023-10-26", "미리보기용 메모입니다. 어두운 테마입니다."))
    }
} 