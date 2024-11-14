package com.ztt.expense.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExpenseItem(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "home_id") val homeId: Long,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "buy_url") val buyUrl: String?,
    @ColumnInfo(name = "purchase_time") val purchaseTime: Long?,
    @ColumnInfo(name = "create_time") val time: Long
)
