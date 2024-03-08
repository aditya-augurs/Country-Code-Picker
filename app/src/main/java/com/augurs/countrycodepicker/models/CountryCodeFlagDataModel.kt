package com.augurs.countrycodepicker.models

import android.graphics.drawable.Drawable

data class CountryCodeFlagDataModel(
    val name: String,
    val code: String,
    val emoji: String,
    val image: String,
    val imageDrawable: Drawable?
)
