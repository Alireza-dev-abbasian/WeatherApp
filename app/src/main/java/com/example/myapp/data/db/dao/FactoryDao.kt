package com.example.myapp.data.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface FactoryDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data:T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data:List<T>)

}