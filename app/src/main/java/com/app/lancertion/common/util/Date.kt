package com.app.lancertion.common.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

class Date: Date() {


    @SuppressLint("SimpleDateFormat")
    fun getNow(): String {
        val ee = SimpleDateFormat("u")
        val mm = SimpleDateFormat("MM")
        val dd = SimpleDateFormat("dd")
        val yyy = SimpleDateFormat("yyyy")

        val day = parseDay(ee.format(this).toInt())
        val mon = parseMon(mm.format(this).toInt())
        val nth = dd.format(this)
        val yy = yyy.format(this)
        return "$day, $nth $mon $yy"
    }

    @SuppressLint("SimpleDateFormat")
    fun getFromPost(datePost: String = "2023-06-15T11:12:13.000Z"): String {

        val year = datePost.slice(0..3)
        val month = datePost.slice(5..6)
        val day = datePost.slice(8..9)

        val formatInput = SimpleDateFormat("dd/MM/yyy")
        val date: Date = formatInput.parse("$day/$month/$year") as Date
        val formatOutput = SimpleDateFormat("u")
        val hari = formatOutput.format(date)

        return "${parseDay(hari.toInt())}, $day ${parseMon(month.toInt())} $year"
    }

    @SuppressLint("SimpleDateFormat")
    fun getGreeting(): String {
        val format = SimpleDateFormat("k")
        return "Selamat ${
            when(format.format(this).toInt()) {
                in 7..10 -> "pagi"
                in 11 .. 14 -> "siang"
                in 15 .. 18 -> "sore"
                else -> "malam"
            }
        }"
    }

    private fun parseDay(day: Int): String {
        return when(day) {
            1 -> "Senin"
            2 -> "Selasa"
            3 -> "Rabu"
            4 -> "Kamis"
            5 -> "Jumat"
            6 -> "Sabtu"
            else -> "Minggu"
        }
    }

    private fun parseMon(mon: Int): String {
        return when(mon) {
            1 -> "Januari"
            2 -> "Februari"
            3 -> "Maret"
            4 -> "April"
            5 -> "Mei"
            6 -> "Juni"
            7 -> "Juli"
            8 -> "Agustus"
            9 -> "September"
            10 -> "Oktober"
            11 -> "November"
            else -> "Desember"
        }
    }
}