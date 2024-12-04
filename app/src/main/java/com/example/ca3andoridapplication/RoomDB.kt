package com.example.ca3andoridapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey




@Entity(tableName = "Resident_Table")
data class RoomDB(
    @PrimaryKey(autoGenerate = true)var id:Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "age") var age: Int,
    @ColumnInfo(name = "room_temp") var room_temp: String
)

