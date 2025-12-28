package me.eroveloc.myapp.domain.repositories

import me.eroveloc.myapp.domain.models.User

interface IUserRepository {
    suspend fun getUser() : User?
    suspend fun addUser(user: User) : Boolean
    suspend fun removeUser() : Boolean
}