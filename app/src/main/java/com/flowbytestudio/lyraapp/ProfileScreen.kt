package com.flowbytestudio.lyraapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * LyraApp Profile & Settings (Profil ve Ayarlar) Screen Implementation
 * Following Material Design 3 and LyraApp Design System
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val surfaceColor = Color(0xFF141317)
    val primaryColor = Color(0xFF7C4DFF)
    val surfaceVariant = Color(0xFF1C1B1F)

    Scaffold(
        containerColor = surfaceColor,
        topBar = {
            TopAppBar(
                title = { Text("LyraApp", color = Color.White, fontWeight = FontWeight.Bold) },
                actions = { IconButton(onClick = { /* Settings */ }) { Icon(Icons.Default.Settings, null, tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                // Profile Photo
                Box(contentAlignment = Alignment.BottomEnd) {
                    Surface(modifier = Modifier.size(120.dp), shape = CircleShape, color = Color.Gray) {}
                    Surface(modifier = Modifier.size(32.dp), shape = CircleShape, color = primaryColor) {
                        Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Edit, null, tint = Color.White, modifier = Modifier.size(16.dp)) }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Alex Thompson", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Premium Member since 2022", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = primaryColor)) {
                    Text("Edit Profile")
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // Settings List
            item {
                SettingsItem(Icons.Default.DarkMode, "Theme", "Switch between light and dark", true)
                SettingsItem(Icons.Default.Notifications, "Notification Settings", "Manage your alerts", false)
                SettingsItem(Icons.Default.AccountCircle, "Account", "Privacy, security, and linked services", false)
                SettingsItem(Icons.Default.Info, "About", "App version, legal, and support", false)
            }
        }
    }
}

@Composable
fun SettingsItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String, isSwitch: Boolean) {
    Surface(
        color = Color(0xFF1C1B1F),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(color = Color.White.copy(alpha = 0.05f), shape = CircleShape, modifier = Modifier.size(40.dp)) {
                Box(contentAlignment = Alignment.Center) { Icon(icon, null, tint = Color.White) }
            }
            Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
                Text(subtitle, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }
            if (isSwitch) {
                var checked by remember { mutableStateOf(true) }
                Switch(checked = checked, onCheckedChange = { checked = it })
            } else {
                Icon(Icons.Default.ChevronRight, null, tint = Color.Gray)
            }
        }
    }
}