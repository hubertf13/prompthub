package pl.prompthub.notification

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Service
import pl.prompthub.security.user.User

@Service
class PushNotificationService() {

    fun sendCopyNotification(receiver: User) {
        val notification = Notification.builder()
            .setTitle("Your prompt was copied")
            .setBody("Some user copied your prompt")
            .build()

        val message = Message.builder()
            .setToken(receiver.fcmToken)
            .setNotification(notification)
            .build()

        FirebaseMessaging.getInstance().send(message)
    }
}