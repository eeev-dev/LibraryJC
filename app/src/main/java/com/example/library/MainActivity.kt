package com.example.library

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.library.ui.theme.LibraryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}

val applicationContext: Context = MainActivity()

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Авторизация", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Войти")
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    val intent = Intent(applicationContext, RegisterActivity::class.java)
                    applicationContext.startActivity(intent)
                },
                modifier = Modifier
                    .weight(1f) // Делим пространство между кнопками
                    .padding(4.dp), // Отступы между кнопками
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF6200EE)
                )
            ) {
                Text(text = "Регистрация")
            }

            Spacer(modifier = Modifier.width(8.dp)) // Отступ между кнопками

            Button(
                onClick = { /* Действие */ },
                modifier = Modifier
                    .weight(1f) // Делим пространство между кнопками
                    .padding(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF6200EE)
                )
            ) {
                Text(text = "Забыли пароль?")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Войти через соцсети")

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.google),
                contentDescription = null,
                Modifier.height(50.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.vk),
                contentDescription = null,
                Modifier.height(50.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.git),
                contentDescription = null,
                Modifier.height(50.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}
