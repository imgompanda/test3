package com.example.todayonelinememo.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todayonelinememo.ui.theme.TodayOneLineMemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    // Later, we'll add parameters for existing memo and save action
) {
    var memoText by remember { mutableStateOf("") }
    // In the future, this state will determine if the user can edit or just view
    val canEdit = true // Placeholder: Assume user can always edit for now

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("오늘의 한 문장") },
                actions = {
                    IconButton(onClick = { navController.navigate("history") }) {
                        Icon(Icons.Filled.History, contentDescription = "메모 기록 보기")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    // Use primaryContainer for AppBar background to match status bar
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    // Adjust title color if needed based on container color
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply Scaffold padding
                .padding(16.dp), // Add outer padding for the content
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Spacing between elements
        ) {
            if (canEdit) {
                OutlinedTextField(
                    value = memoText,
                    onValueChange = { memoText = it },
                    label = { Text("오늘의 한 문장을 기록하세요") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Take available vertical space
                    shape = MaterialTheme.shapes.medium, // Apply rounded corners
                    minLines = 5
                )
                // Use FilledTonalButton for a softer, cuter look
                FilledTonalButton(
                    onClick = {
                        // Placeholder: In the future, save memoText
                        println("Save clicked: $memoText")
                        // Possibly navigate away or show confirmation
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium // Apply rounded corners
                ) {
                    Text("저장하기")
                }
            } else {
                // Placeholder: Display today's memo if it exists and cannot be edited
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text(
                        text = "오늘 이미 메모를 작성했습니다.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Main Screen Light")
@Composable
fun MainScreenPreviewLight() {
    TodayOneLineMemoTheme(darkTheme = false) {
        MainScreen(rememberNavController())
    }
}

@Preview(showBackground = true, name = "Main Screen Dark")
@Composable
fun MainScreenPreviewDark() {
    TodayOneLineMemoTheme(darkTheme = true) {
        MainScreen(rememberNavController())
    }
} 