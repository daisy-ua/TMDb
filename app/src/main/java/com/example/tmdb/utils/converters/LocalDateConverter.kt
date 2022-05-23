package com.example.tmdb.utils.converters

import android.annotation.SuppressLint
import java.time.LocalDate

@SuppressLint("NewApi")
fun getYear(date: String) = LocalDate.parse(date).year
