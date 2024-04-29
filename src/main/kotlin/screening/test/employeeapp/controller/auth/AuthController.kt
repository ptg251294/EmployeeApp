package screening.test.employeeapp.controller.auth

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import screening.test.employeeapp.service.auth.AuthenticationService

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthenticationService
) {

    @PostMapping
    fun login(
        @RequestBody authRequest: AuthenticationRequest
    ): AuthenticationResponse {
        val tokenPair = authService.authentication(authRequest)
        return AuthenticationResponse(accessToken = tokenPair.first, refreshToken = tokenPair.second)
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody tokenRefreshRequest: TokenRefreshRequest
    ): RefreshTokenResponse =
        authService.refreshToken(tokenRefreshRequest.token)
            ?.let { RefreshTokenResponse(token = it) }
            ?: throw RuntimeException("Error generating refresh token")
}