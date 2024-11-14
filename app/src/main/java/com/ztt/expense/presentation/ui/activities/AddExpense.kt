package com.ztt.expense.presentation.ui.activities


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ztt.expense.R
import com.ztt.expense.common.ToastHelper
import com.ztt.expense.presentation.theme.ExpenseTheme
import com.ztt.expense.presentation.viewmodels.AddExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class AddExpense : ComponentActivity() {

    private var expenseName = mutableStateOf("")
    private var selectedDate = mutableLongStateOf(0)
    private val priceText = mutableStateOf("")

    private var step = mutableIntStateOf(1)

    private val priceNumbers: MutableList<String> = mutableListOf()

    private var _userId: Long = -1
    private var _homeId: Long = -1

    private val vm: AddExpenseViewModel by viewModels()

    init {
        for (i in 1..9) {
            priceNumbers.add(i.toString())
        }
        priceNumbers.add("0")
        priceNumbers.add(".")
        priceNumbers.add("confirm")
        priceNumbers.add("back")
        priceNumbers.add("close")
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _userId = intent.getLongExtra("user_id", -1)
        _homeId = intent.getLongExtra("home_id", -1)

        setContent {
            ExpenseTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(getString(R.string.add_expense))
                            }
                        )
                    }) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // progress bar
                        Row(
                            modifier = Modifier
                                .height(60.dp)
                                .padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            StepDot(true)
                            StepLine()
                            StepText(1, step.intValue >= 1)
                            StepLine()
                            StepText(2, step.intValue >= 2)
                            StepLine()
                            StepText(3, step.intValue >= 3)
                            StepLine()
                            StepDot(false)
                        }
                        // content area
                        when (step.intValue) {
                            1 -> {
                                InputExpenseName()
                            }

                            2 -> {
                                SelectDate()
                            }

                            3 -> {
                                SelectPrice()
                            }
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.addExpenseState.collect {
                    if (it != -1L) {
                        // success
                        val intent = Intent()
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }
            }
        }
    }

    @Composable
    fun InputExpenseName() {
        val keyboardController = LocalSoftwareKeyboardController.current
        val scope = rememberCoroutineScope()
        OutlinedTextField(
            value = expenseName.value,
            onValueChange = {
                expenseName.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 30.dp, start = 20.dp, end = 20.dp),
            label = {
                Text(getString(R.string.expense_name_label), color = Color.Gray, fontSize = 12.sp)
            }
        )
        ElevatedButton(
            onClick = {
                scope.launch {
                    keyboardController?.hide()
                    delay(300)
                    if (expenseName.value.isEmpty()) {
                        ToastHelper.showToast(
                            applicationContext,
                            getString(R.string.expense_name_empty)
                        )
                    } else {
                        step.intValue = 2
                    }
                }
            },
        ) {
            Text(getString(R.string.complete), fontSize = 16.sp)
        }
    }


    @Composable
    fun SelectDate() {
        val datePickerState = rememberDatePickerState()
        selectedDate.longValue = datePickerState.selectedDateMillis ?: 0
        DatePickerDialog(
            onDismissRequest = {},
            confirmButton = {
                ElevatedButton(onClick = {
                    val selected = datePickerState.selectedDateMillis
                    if (selected == null) {
                        ToastHelper.showToast(
                            applicationContext,
                            getString(R.string.expense_date_incorrect)
                        )
                    } else {
                        step.intValue = 3
                    }
                }) {
                    Text(getString(R.string.confirm))
                }
            }, dismissButton = {
                ElevatedButton(onClick = {
                    step.intValue = 1
                }) {
                    Text(getString(R.string.cancel))
                }
            }) {
            DatePicker(
                state = datePickerState
            )
        }
    }

    @Composable
    fun SelectPrice() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.padding(bottom = 30.dp, top = 30.dp, start = 20.dp, end = 20.dp)
        ) {
            items(count = priceNumbers.size) { it ->
                val str = priceNumbers[it]
                ElevatedButton(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                    onClick = {
                        if (str == "confirm") {
                            if (priceText.value.toDoubleOrNull() == null) {
                                ToastHelper.showToast(
                                    applicationContext,
                                    getString(R.string.expense_price_incorrect)
                                )
                            } else {
                                vm.addNewExpense(
                                    _userId, _homeId,
                                    expenseName.value,
                                    priceText.value,
                                    selectedDate.longValue
                                )
                            }
                        } else if (str == "back") {
                            step.intValue = 2
                        } else if (str == "close") {
                            finish()
                        } else {
                            priceText.value += str
                        }
                    }) {
                    when (str) {
                        "confirm" -> {
                            Icon(Icons.Filled.Done, "Done")
                        }

                        "back" -> {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }

                        "close" -> {
                            Icon(Icons.Filled.Close, "Close")
                        }

                        else -> {
                            Text(str)
                        }
                    }
                }
            }
        }
        Box {
            Text(text = priceText.value, fontSize = 22.sp, fontWeight = FontWeight.W600)
        }
    }

}


@Composable
fun StepDot(isCompleted: Boolean) {
    Box(
        modifier = Modifier
            .size(10.dp)  // Larger dot for visibility
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
            .background(if (isCompleted) Color.Yellow else Color.White)
    )
}

@Composable
fun StepText(stepNumber: Int, finishedStep: Boolean) {
    Box(
        modifier = Modifier
            .size(40.dp)  // Circle large enough for text
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)
            .background(if (finishedStep) Color.Yellow else Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stepNumber.toString(),
            color = Color.Black,  // Ensure the text is visible
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun StepLine() {
    Spacer(
        modifier = Modifier
            .width(40.dp)  // Space between the circles (dots and text)
            .height(2.dp)  // Line thickness
            .background(Color.Gray)  // Line color
    )
}
