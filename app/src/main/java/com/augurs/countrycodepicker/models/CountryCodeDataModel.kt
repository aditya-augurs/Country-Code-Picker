package com.augurs.countrycodepicker.models

data class CountryCodeDataModel(
    val name: String? = null,
    val dialCode: String? = null,
    val code: String? = null,
    val data: CountryCodeFlagDataModel? = null
)
