package com.ztt.expense.presentation.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ztt.expense.R
import com.ztt.expense.presentation.theme.ExpenseTheme
import com.ztt.expense.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(getString(R.string.expense_list))
                            },
                        )
                    },) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                    }
                }
            }
        }
        vm.init()
    }
}



