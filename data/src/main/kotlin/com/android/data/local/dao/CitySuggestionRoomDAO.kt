package com.android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.data.local.entities.CitySuggestionRoomEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CitySuggestionRoomDAO {

    @Query("SELECT * FROM weather")
    fun getSuggestionCities(): Flowable<List<CitySuggestionRoomEntity>>

    @Query("DELETE FROM weather")
    fun clearAll(): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: List<CitySuggestionRoomEntity>): Completable
}