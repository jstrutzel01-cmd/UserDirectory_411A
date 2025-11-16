package com.example.userdirectory_411a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.userdirectory_411a.data.getDatabase
import com.example.userdirectory_411a.repository.UserRepository
import com.example.userdirectory_411a.ui.theme.UserDirectory_411ATheme
import com.example.userdirectory_411a.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dao = getDatabase(application).userDao()
        val repository = UserRepository(dao)
        val viewModel = UserViewModel(repository)
        setContent {
            UserDirectory_411ATheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: UserViewModel, modifier: Modifier = Modifier) {
    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {
        Text("Loading: $isLoading")
        Text("User count: ${users.size}")
        users.forEach { user ->
            Text(user.name)
        }
    }
}

}




