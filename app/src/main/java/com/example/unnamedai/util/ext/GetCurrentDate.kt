package com.example.unnamedai.util.ext

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getCurrentDate(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd, MM, yyyy")

    return current.format(formatter)
}