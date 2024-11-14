package com.ztt.expense.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.material.expandable.ExpandableWidget
import com.ztt.expense.R
import com.ztt.expense.common.DateHelper
import com.ztt.expense.data.local.database.entity.ExpenseItem
import com.ztt.expense.presentation.theme.ExpenseTheme
import com.ztt.expense.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()

    private val addExpenseRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // TODO maybe no need
            }
        }

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
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            val intent = Intent(this, AddExpense::class.java)
                            intent.putExtra("user_id", vm.getUserId())
                            intent.putExtra("home_id", vm.getHomeId())
                            addExpenseRequest.launch(intent)
                        }) {
                            Icon(Icons.Filled.Add, "Add Expense")
                        }
                    }) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        val list = vm.listState.collectAsStateWithLifecycle()
                        ExpenseList(list.value)
                    }
                }
            }
        }
        vm.init()
    }


    @Composable
    fun ExpenseList(expenseList: List<ExpenseItem>) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(expenseList) { it ->
                ExpenseItemUI(it)
            }
        }
    }

    @Composable
    fun ExpenseItemUI(item: ExpenseItem) {
        Box {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 12.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = { offset ->

                            }
                        )
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 6.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.name ?: "",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = DateHelper.getFormatedDate(item.purchaseTime ?: 0L),
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.W600,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Yellow,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 6.dp)
                            .widthIn(min = 50.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = item.price ?: "",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            modifier = Modifier
                                .align(Alignment.Center),
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                vm.deleteExpense(item)
                            }
                    ) {
                        Icon(
                            Icons.Filled.Delete, "Delete", modifier = Modifier
                                .size(22.dp)
                                .align(Alignment.Center),
                            tint = Color.Red
                        )
                    }
                }
            }
        }

    }
}



