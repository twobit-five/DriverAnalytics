package com.twobit.driver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.twobit.driver.data.entities.Event

@Dao
interface EventDao {
    @Insert
    suspend fun insert(event: Event)

    @Query("SELECT * FROM Event")
    suspend fun getAllEvents(): List<Event>
}