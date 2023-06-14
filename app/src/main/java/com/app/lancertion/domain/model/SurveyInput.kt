package com.app.lancertion.domain.model

data class SurveyInput(
    val alcohol_use: Int,
    val dust_allergy: Int,
    val genetic_risk: Int,
    val balanced_diet: Int,
    val obesity: Int,
    val smoker: Int,
    val coughing_of_blood: Int
)