package com.example.diplom2

data class LocationClass (val id : String, val latitude : Double, val longitude : Double)
data class OrderClass (val id : String, val cart : String, val price : Int, val tableName : String, val time : String, val date : String)
data class OrderHomeClass (val id : String, val cart : String, val price : Int, val location : String)
data class TablesClass (val id : String, val name : String, val table : String, val time : String, val date : String)