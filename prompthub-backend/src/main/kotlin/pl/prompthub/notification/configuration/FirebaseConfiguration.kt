package pl.prompthub.notification.configuration

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.annotation.PostConstruct

@Configuration
class FirebaseConfiguration {

    private val log = LoggerFactory.getLogger(FirebaseConfiguration::class.java)

    @PostConstruct
    fun initializeFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {

                val serviceAccount = ClassPathResource("prompthub-766d6-firebase-adminsdk-fbsvc-780ff13b3b.json").inputStream

                val options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build()

                FirebaseApp.initializeApp(options)
                log.info("Firebase application has been initialized")
            }
        } catch (e: Exception) {
            log.error("Error initializing Firebase: {}", e.message)
        }
    }
}