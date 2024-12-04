package com.example.ca3andoridapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ca3andoridapplication.RoomDB

@Dao
interface RoomDao {
    @Insert
    suspend fun insertResident(rd: RoomDB):Long

//    @Query("SELECT * FROM Resident_Table")
//    suspend fun getAllResident(): List<RoomDB> // Return all residents

    @Query("SELECT * FROM Resident_Table WHERE name = :username AND password = :password")
    suspend fun validateResident(username: String, password: String): RoomDB? // Return null if no match
}
