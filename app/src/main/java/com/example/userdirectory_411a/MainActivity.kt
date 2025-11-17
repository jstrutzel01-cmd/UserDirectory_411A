package com.example.userdirectory_411a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.userdirectory_411a.data.User
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
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {

        TextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search by name or email") },
            singleLine = true

        )


        Text("Loading: $isLoading")

        LazyColumn {
            items(users) { user ->
                UserCard(user)

            }
        }
    }
}
    @Composable
    fun UserCard(user: User) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("ID: ${user.id}", style = MaterialTheme.typography.bodySmall)
                Text(user.name, style = MaterialTheme.typography.titleMedium)
                Text(user.email, style = MaterialTheme.typography.bodyMedium)
                Text(user.phone, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }



}




