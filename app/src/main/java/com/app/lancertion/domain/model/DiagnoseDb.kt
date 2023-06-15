package com.app.lancertion.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("diagnose_db")
data class DiagnoseDb(
    val date: String,
    val alcoholUse: Int,
    val balacedDiet: Int,
    val coughingOfBloodval : Int,
    val dustAllergy: Int,
    val geneticRisk: Int,
    val obesity: Int,
    val smoker: Int,
    val result: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
