package pl.prompthub.notification

import org.springframework.stereotype.Service
import pl.prompthub.notification.dto.UpdateFcmTokenRequest
import pl.prompthub.security.facade.AuthenticationFacade
import pl.prompthub.security.user.UserRepository

@Service
class FcmTokenService(
    private val userRepository: UserRepository,
    private val authenticationFacade: AuthenticationFacade
) {

    fun updateFcmToken(request: UpdateFcmTokenRequest) {
        val user = authenticationFacade.authenticatedUser()

        user.fcmToken = request.fcmToken
        userRepository.save(user)
    }
}