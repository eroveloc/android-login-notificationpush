package me.eroveloc.myapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
)

