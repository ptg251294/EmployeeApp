package screening.test.employeeapp.service.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import screening.test.employeeapp.config.JwtProperties
import java.util.*

@Service
class TokenService(
    jwtProperties: JwtProperties
) {
    private val secret = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    /**
     * Function to generate the JWT token for provided user signed with the secret in application config having
     * validity till provided expiration date.
     */
    fun generateToken(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(secret)
            .compact()

    /**
     * Function to check the validity of input JWT token. It is checked against the credentials of logged-in
     * user.
     */
    fun isValid(token: String, userDetails: UserDetails): Boolean {

        val username = getUsernameFromToken(token)

        return username == userDetails.username && isExpired(token).not()
    }

    /**
     * Function to extract username from provided JWT token
     */
    fun getUsernameFromToken(token: String): String =
        getClaims(token).subject

    /**
     * Function to check if the provided token has expired.
     */
    fun isExpired(token: String): Boolean =
        getClaims(token).expiration.before(Date(System.currentTimeMillis()))

    private fun getClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(secret)
            .build()

        return parser.parseSignedClaims(token).payload
    }
}