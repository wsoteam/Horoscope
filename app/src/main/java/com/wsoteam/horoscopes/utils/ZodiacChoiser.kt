package com.wsoteam.horoscopes.utils

object ZodiacChoiser {

    fun choiceSign(date : String) : Int{
        var day = date.split(".")[0].toInt()
        var month = date.split(".")[1].toInt()
        var signIndex = -1

        when(month){
            1 -> {
                signIndex = if (day > 20){
                    10
                }else{
                    9
                }
            }
            2 -> {
                signIndex = if (day > 18){
                    11
                }else{
                    10
                }
            }
            3 -> {
                signIndex = if (day > 20){
                    0
                }else{
                    11
                }
            }
            4 -> {
                signIndex = if (day > 20){
                    1
                }else{
                    0
                }
            }
            5 -> {
                signIndex = if (day > 21){
                    2
                }else{
                    1
                }
            }
            6 -> {
                signIndex = if (day > 21){
                    3
                }else{
                    2
                }
            }
            7 -> {
                signIndex = if (day > 22){
                    4
                }else{
                    3
                }
            }
            8 -> {
                signIndex = if (day > 23){
                    5
                }else{
                    4
                }
            }
            9 -> {
                signIndex = if (day > 23){
                    6
                }else{
                    5
                }
            }
            10 -> {
                signIndex = if (day > 22){
                    7
                }else{
                    6
                }
            }
            11 -> {
                signIndex = if (day > 22){
                    8
                }else{
                    7
                }
            }
            12 -> {
                signIndex = if (day > 21){
                    9
                }else{
                    8
                }
            }
        }

        return signIndex
    }
}