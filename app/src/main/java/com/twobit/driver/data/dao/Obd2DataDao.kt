package com.twobit.driver.data.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.twobit.driver.data.entities.Obd2Data

@Dao
interface Obd2DataDao {
    @Insert
    suspend fun insert(obd2Data: Obd2Data)

    @Query("SELECT * FROM obd2_data WHERE isUploaded = 0")
    suspend fun getNonUploaded(): List<Obd2Data>

    @Query("UPDATE obd2_data SET isUploaded = 1 WHERE id IN (:ids)")
    suspend fun markAsUploaded(ids: List<Int>)
}
