package com.app.lancertion.data.remote.request

import com.google.gson.annotations.SerializedName

data class DiagnoseBody (
    @SerializedName("alcohol_use") val alcoholUse: Int,
    @SerializedName("balanced_diet") val balacedDiet: Int,
    @SerializedName("coughing_of_blood") val coughingOfBloodval : Int,
    @SerializedName("dust_allergy") val dustAllergy: Int,
    @SerializedName("genetic_risk") val geneticRisk: Int,
    val obesity: Int,
    val smoker: Int
)
