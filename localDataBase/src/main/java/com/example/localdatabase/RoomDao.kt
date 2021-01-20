package com.example.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomDao {

    @Insert
    suspend fun insertItem(entity: Entity)

    @Query("select * from  itemDetailsTable where dataUploadDate= :dataUploadDate ")
    fun getItemsList(dataUploadDate:String): LiveData<List<Entity>>
}