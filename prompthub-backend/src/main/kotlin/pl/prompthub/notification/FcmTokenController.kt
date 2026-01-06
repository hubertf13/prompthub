package pl.prompthub.notification

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.prompthub.notification.dto.UpdateFcmTokenRequest

@RestController
@RequestMapping("/api/v1/fcm-token")
class FcmTokenController(
    private val fcmTokenService: FcmTokenService
) {

    @PatchMapping("/me")
    fun updateFcmToken(@RequestBody request: UpdateFcmTokenRequest): ResponseEntity<Unit> =
        ResponseEntity.ok(fcmTokenService.updateFcmToken(request))
}