package screening.test.employeeapp.service.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import screening.test.employeeapp.config.JwtProperties
import screening.test.employeeapp.controller.auth.AuthenticationRequest
import screening.test.employeeapp.repository.RefreshTokenRepository
import java.util.Date

/**
 * Class providing authentication endpoint functionalities
 */

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    /**
     * Function to check authenticity of username and password combination provided in the request. It returns access
     * token and refresh token as a String Pair.
     */
    fun authentication(authRequest: AuthenticationRequest): Pair<String, String> {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.username,
                authRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authRequest.username)
        val accessToken = getAccessToken(user)
        val refreshToken = getRefreshToken(user)

        refreshTokenRepository.tokens[refreshToken] = user

        return accessToken?.let { Pair(it, refreshToken) } ?: throw UsernameNotFoundException("User not found")
    }

    private fun getRefreshToken(user: UserDetails) = tokenService.generateToken(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
    )

    private fun getAccessToken(user: UserDetails?): String? {

        return user?.let {
            val token = tokenService.generateToken(
                userDetails = it,
                expirationDate = Date(System.currentTimeMillis() + jwtProperties.tokenExpiration)
            )

            token
        }
    }

    /**
     * Function to generate access token using the provided refresh token.
     */
    fun refreshToken(token: String): String? {
        val username = tokenService.getUsernameFromToken(token)

        return username.let {
            val currentUserDetails = userDetailsService.loadUserByUsername(it)
            val refreshTokenUserDetails = refreshTokenRepository.findByToken(token)

            if (!tokenService.isExpired(token) &&
                currentUserDetails.username == refreshTokenUserDetails?.username
            )
                getAccessToken(currentUserDetails)
            else
                null
        }
    }

}
