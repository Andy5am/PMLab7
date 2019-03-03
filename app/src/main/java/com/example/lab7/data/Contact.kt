package com.example.lab7.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact(
    var name: String,

    var phone:String,

    var mail :String,

    var priority: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}