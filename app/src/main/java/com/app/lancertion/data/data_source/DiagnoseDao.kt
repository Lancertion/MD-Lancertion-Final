package com.app.lancertion.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.lancertion.domain.model.DiagnoseDb
import kotlinx.coroutines.flow.Flow

@Dao
interface DiagnoseDao {

    @Query("SELECT * FROM diagnose_db")
    fun getDiagnoses(): Flow<List<DiagnoseDb>>

    @Query("SELECT * FROM diagnose_db WHERE id = :id")
    suspend fun getDiagnose(id: Int): DiagnoseDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDiagnose(diagnose: DiagnoseDb)

    @Delete
    suspend fun deleteDiagnose(diagnose: DiagnoseDb)
}

