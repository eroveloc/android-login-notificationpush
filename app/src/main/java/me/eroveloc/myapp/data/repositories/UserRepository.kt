package me.eroveloc.myapp.data.repositories

import me.eroveloc.myapp.data.local.daos.UserDao
import me.eroveloc.myapp.data.local.entities.UserEntity
import me.eroveloc.myapp.domain.models.User
import me.eroveloc.myapp.domain.repositories.IUserRepository

class UserRepository(
    private  val userDao: UserDao

) : IUserRepository {
    override suspend fun getUser(): User? {
        // Obtenemos la entidad de la BD
        val userEntity = userDao.getUser()

        // Mapeamos de Entity -> Domain Model
        // Si es null, deberás decidir qué devolver o lanzar una excepción
        return userEntity?.let {
            User(
                id = it.id,
                name = it.name,
                email = it.email
            )
        }
    }

    override suspend fun addUser(user: User): Boolean {
        return try {
            // Mapeamos de Domain Model -> Entity
            val entity = UserEntity(
                id = user.id,
                name = user.name,
                email = user.email
            )
            userDao.insertUser(entity)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun removeUser(): Boolean {
        return try {
            userDao.clearUser()
            true
        }catch (e:Exception) {
            e.printStackTrace()
            false

        }
    }
}