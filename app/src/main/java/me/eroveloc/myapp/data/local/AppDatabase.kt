package me.eroveloc.myapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.eroveloc.myapp.data.local.daos.UserDao
import me.eroveloc.myapp.data.local.entities.UserEntity

// Define las entidades que forman la base de datos
// Define la versión (incrementa si cambias la estructura de la tabla)
@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}