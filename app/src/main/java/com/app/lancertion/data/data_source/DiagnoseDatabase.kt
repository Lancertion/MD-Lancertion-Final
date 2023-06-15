package com.app.lancertion.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.lancertion.domain.model.DiagnoseDb

@Database(
    entities = [DiagnoseDb::class],
    version = 1
)
abstract class DiagnoseDatabase: RoomDatabase() {

    abstract val DiagnoseDao: DiagnoseDao

    companion object {
        const val DATABASE_NAME = "lancertion_diagnose"
    }

}