package screening.test.employeeapp.repository

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepository {

    val tokens = mutableMapOf<String, UserDetails>()

    fun findByToken(token: String): UserDetails? =
        tokens[token]
}