package com.example.localdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemDetailsTable")
class Entity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int = 0,
        @ColumnInfo(name = "latitude")
        val latitude: Double,
        @ColumnInfo(name = "longitude")
        val longitude: Double,
        @ColumnInfo(name = "dataUploadTime")
        val dataUploadtime: String?,
        @ColumnInfo(name = "dataUploadDate")
        val dataUploaddate: String?
)