package com.example.tmdb.utils.converters

import com.tmdb.models.Country

fun getCountriesString(list: List<Country>): String {
    var countries = ""
    for (country in list) {
        countries += country.name + ", "
    }
    return countries.dropLast(2)
}