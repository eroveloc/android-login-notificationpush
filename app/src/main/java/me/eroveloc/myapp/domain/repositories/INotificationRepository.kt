package me.eroveloc.myapp.domain.repositories

interface INotificationRepository {
    suspend fun updateToken(token: String)
    fun showNotification(title: String, body: String)
}