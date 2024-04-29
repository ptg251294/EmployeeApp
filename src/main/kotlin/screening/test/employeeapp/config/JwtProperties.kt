package screening.test.employeeapp.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtProperties(
    val key: String,
    val tokenExpiration: Long,
    val refreshTokenExpiration: Long,
)
