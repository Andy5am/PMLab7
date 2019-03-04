package com.example.ejemplo26feb.data
import androidx.room.Entity

@Entity(tableName = "note_table")
data class Note(

    var title: String,
    var description: String,
    var priority: Int
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}