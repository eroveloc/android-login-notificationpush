package me.eroveloc.myapp.data.services

import androidx.activity.result.launch
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.eroveloc.myapp.domain.repositories.INotificationRepository
import org.koin.android.ext.android.inject

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private  val _notificationRepository : INotificationRepository by inject()
    private  val _serviceScope = CoroutineScope(Dispatchers.IO)
    
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // 1. Manejar carga de datos (Data Payload)
        // Esto ocurre si envías datos personalizados desde tu servidor
        message.data.isNotEmpty().let {
            // Aquí podrías procesar datos silenciosos
        }

        // 2. Manejar notificación (Notification Payload)
        // Esto ocurre si envías el campo "notification" desde Firebase Console o Server
        message.notification?.let {
            _notificationRepository.showNotification(
                title = it.title ?: "Notificación",
                body = it.body ?: ""
            )
        }

        // Caso especial: Si envías solo "data" y quieres mostrar notificación manual
        if (message.notification == null && message.data.isNotEmpty()) {
            _notificationRepository.showNotification(
                title = message.data["title"] ?: "Nuevo Mensaje",
                body = message.data["body"] ?: ""
            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // Enviamos el token al backend usando corrutinas
        _serviceScope.launch {
            _notificationRepository.updateToken(token)
        }
    }
}