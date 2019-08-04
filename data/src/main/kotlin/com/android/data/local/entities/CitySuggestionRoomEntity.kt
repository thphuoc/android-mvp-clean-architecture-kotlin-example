package com.android.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class CitySuggestionRoomEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        @ColumnInfo(name = "area")
        val area: String,
        @ColumnInfo(name = "region")
        val region: String,
        @ColumnInfo(name = "country")
        val country: String
)