package com.app.lancertion.data.remote.dto

import com.app.lancertion.data.remote.request.DiagnoseBody
import com.google.gson.annotations.SerializedName

data class Input(
    @SerializedName("alcohol_use") val alcoholUse: Int,
    @SerializedName("balanced_diet") val balacedDiet: Int,
    @SerializedName("coughing_of_blood") val coughingOfBloodval : Int,
    @SerializedName("dust_allergy") val dustAllergy: Int,
    @SerializedName("genetic_risk") val geneticRisk: Int,
    val obesity: Int,
    val smoker: Int
)

fun Input.asDiagnoseBody(): DiagnoseBody {
    return DiagnoseBody(
        alcoholUse = alcoholUse,
        balacedDiet = balacedDiet,
        coughingOfBloodval = coughingOfBloodval,
        dustAllergy = dustAllergy,
        geneticRisk = geneticRisk,
        obesity = obesity,
        smoker = smoker
    )
}